package com.expressbank.paydaytray.service;

import com.expressbank.paydaytray.dto.request.UserRequest;
import com.expressbank.paydaytray.dto.response.ResponseModel;
import com.expressbank.paydaytray.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    ResponseModel<UserResponse> register(UserRequest userRequest);

    void confirmAccount(String token);

    ResponseModel<List<UserResponse>> getAllUsers();

    ResponseModel<UserResponse> getUserById(Long id);
}
