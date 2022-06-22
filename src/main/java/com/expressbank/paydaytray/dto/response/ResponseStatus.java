package com.expressbank.paydaytray.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStatus {
    private String message;
    private Integer code;

    public static ResponseStatus getSuccess(){
        ResponseStatus status = new ResponseStatus();
        status.setMessage("Success");
        status.setCode(100);
        return status;
    }
}
