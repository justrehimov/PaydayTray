package com.expressbank.paydaytray.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String email;
    private Double balance;
}
