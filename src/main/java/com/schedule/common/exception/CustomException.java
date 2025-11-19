package com.schedule.common.exception;

import com.schedule.common.enums.ExceptionCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;      //예외 Enum

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
