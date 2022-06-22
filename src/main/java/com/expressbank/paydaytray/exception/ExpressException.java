package com.expressbank.paydaytray.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExpressException extends Exception{
    private String message;
    private Integer code;

    public ExpressException(String message){
        super(message);
    }
}
