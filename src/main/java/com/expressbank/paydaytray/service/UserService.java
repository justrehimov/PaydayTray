package com.expressbank.paydaytray.service;

import com.expressbank.paydaytray.dto.request.UserRequest;
import com.expressbank.paydaytray.dto.response.ResponseModel;
import com.expressbank.paydaytray.dto.response.UserResponse;

public interface UserService {
    ResponseModel<UserResponse> register(UserRequest userRequest);

    void confirmAccount(String token);
}
