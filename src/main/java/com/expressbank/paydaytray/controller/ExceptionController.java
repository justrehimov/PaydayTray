package com.expressbank.paydaytray.controller;


import com.expressbank.paydaytray.dto.response.ResponseStatus;
import com.expressbank.paydaytray.exception.ErrorCode;
import com.expressbank.paydaytray.exception.ExpressException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(ExpressException.class)
    public ResponseStatus handleExpressException(ExpressException ex){
        return ResponseStatus.builder()
                .message(ex.getMessage())
                .code(ex.getCode())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseStatus handleValidationException(BindingResult bindingResult){
        String message = "";
        for (ObjectError e:bindingResult.getAllErrors()){
            message += e.getDefaultMessage() + "\n";
        }
        return ResponseStatus.builder()
                .message(message)
                .code(ErrorCode.VALIDATION_ERROR)
                .build();
    }
}
