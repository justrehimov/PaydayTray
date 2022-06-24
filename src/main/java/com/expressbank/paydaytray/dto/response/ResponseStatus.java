package com.expressbank.paydaytray.dto.response;

import com.expressbank.paydaytray.exception.ErrorCode;
import com.expressbank.paydaytray.exception.ErrorMessage;
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
        status.setMessage(ErrorMessage.SUCCESS);
        status.setCode(ErrorCode.SUCCESS);
        return status;
    }
}
