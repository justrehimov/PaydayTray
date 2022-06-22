package com.expressbank.paydaytray.dto.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequest {
    @NotNull
    @NotBlank
    @Size(min = 2)
    private String username;
    @NotNull
    @NotBlank
    @Size(min = 6)
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 6)
    private String password;
}
