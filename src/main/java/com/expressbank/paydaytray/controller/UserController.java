package com.expressbank.paydaytray.controller;

import com.expressbank.paydaytray.dto.response.ResponseModel;
import com.expressbank.paydaytray.dto.response.UserResponse;
import com.expressbank.paydaytray.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseModel<List<UserResponse>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseModel<UserResponse> getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

}
