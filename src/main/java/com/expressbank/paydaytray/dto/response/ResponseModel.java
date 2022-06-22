package com.expressbank.paydaytray.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseModel<T> {
    private T model;
    private ResponseStatus status;
}
