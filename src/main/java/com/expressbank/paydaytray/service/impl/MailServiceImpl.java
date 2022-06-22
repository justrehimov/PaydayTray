package com.expressbank.paydaytray.service.impl;

import com.expressbank.paydaytray.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendMail(SimpleMailMessage mailMessage) {
       javaMailSender.send(mailMessage);
    }

    @Override
    @Async
    public void sendMail(MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }
}
