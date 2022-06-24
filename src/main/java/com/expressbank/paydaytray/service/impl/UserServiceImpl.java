package com.expressbank.paydaytray.service.impl;

import com.expressbank.paydaytray.auth.ConfirmationToken;
import com.expressbank.paydaytray.auth.User;
import com.expressbank.paydaytray.dto.request.UserRequest;
import com.expressbank.paydaytray.dto.response.ResponseModel;
import com.expressbank.paydaytray.dto.response.ResponseStatus;
import com.expressbank.paydaytray.dto.response.UserResponse;
import com.expressbank.paydaytray.exception.ErrorCode;
import com.expressbank.paydaytray.exception.ErrorMessage;
import com.expressbank.paydaytray.exception.ExpressException;
import com.expressbank.paydaytray.repository.ConfirmationTokenRepo;
import com.expressbank.paydaytray.repository.UserRepo;
import com.expressbank.paydaytray.service.ConfirmationTokenService;
import com.expressbank.paydaytray.service.MailService;
import com.expressbank.paydaytray.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ExpressionException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ConfirmationTokenRepo tokenRepo;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Value("${server.domain}")
    private String domain;


    @SneakyThrows
    @Override
    @Transactional
    public ResponseModel<UserResponse> register(UserRequest userRequest) {
        User requestUser = modelMapper.map(userRequest,User.class);
        Optional<User> userOptional =
                userRepo.findUserByEmailOrUsername(requestUser.getEmail(), requestUser.getUsername());
        if(userOptional.isPresent())
            throw new ExpressException(ErrorMessage.USER_ALREADY_EXISTS, ErrorCode.USER_ALREADY_EXISTS);
        requestUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User savedUser = userRepo.save(requestUser);
        ConfirmationToken token = new ConfirmationToken(savedUser);
        ConfirmationToken savedToken = tokenRepo.save(token);
        sendConfirmMail(savedToken);
        return ResponseModel.<UserResponse>builder()
                .model(modelMapper.map(savedUser,UserResponse.class))
                .status(ResponseStatus.getSuccess())
                .build();
    }

    @Override
    @SneakyThrows
    @Transactional
    public void confirmAccount(String token) {
        ConfirmationToken confirmationToken = tokenRepo.findByToken(token)
                .orElseThrow(()->new ExpressException(ErrorMessage.TOKEN_NOT_FOUND,ErrorCode.TOKEN_NOT_FOUND));
        if(!confirmationToken.getCreatedAt().before(confirmationToken.getExpiredAt()))
            throw new ExpressException(ErrorMessage.TOKEN_HAS_EXPIRED, ErrorCode.TOKEN_HAS_EXPIRED);
        User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepo.save(user);
    }

    @Override
    @SneakyThrows
    public ResponseModel<List<UserResponse>> getAllUsers() {
        List<User> users = userRepo.findAll();
        if(users.isEmpty())
            throw new ExpressException(ErrorMessage.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND);
        List<UserResponse> userResponses = users.stream()
                .map(u->modelMapper.map(u,UserResponse.class))
                .collect(Collectors.toList());
        return ResponseModel.<List<UserResponse>>builder()
                .model(userResponses)
                .status(ResponseStatus.getSuccess())
                .build();
    }

    @Override
    @SneakyThrows
    public ResponseModel<UserResponse> getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(()->new ExpressException(ErrorMessage.USER_NOT_FOUND,ErrorCode.USER_NOT_FOUND));
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        return ResponseModel.<UserResponse>builder()
                .model(userResponse)
                .status(ResponseStatus.getSuccess())
                .build();
    }

    @SneakyThrows
    private void sendConfirmMail(ConfirmationToken token){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("Express");
        helper.setTo(token.getUser().getEmail());
        helper.setSubject("Confirm your email address");
        String link = domain + "auth/confirm/"+token.getToken();
        String text = "<p>Hi, " + token.getUser().getUsername() + "</p><br>" +
                "Please click <a href='"+ link+"'>here</a> and confirm your email address";
        helper.setText(text, true);
        javaMailSender.send(message);
    }
}
