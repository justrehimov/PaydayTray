package com.expressbank.paydaytray.controller;

import com.expressbank.paydaytray.dto.request.UserRequest;
import com.expressbank.paydaytray.dto.response.ResponseModel;
import com.expressbank.paydaytray.dto.response.ResponseStatus;
import com.expressbank.paydaytray.dto.response.UserResponse;
import com.expressbank.paydaytray.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseModel<UserResponse> register(@Valid @RequestBody UserRequest userRequest){
       return userService.register(userRequest);
    }

    @GetMapping("/confirm/{token}")
    public ResponseStatus confirmAccount(@PathVariable("token") String token){
        userService.confirmAccount(token);
        return ResponseStatus.getSuccess();
    }
}
