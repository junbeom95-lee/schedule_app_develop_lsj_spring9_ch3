package com.schedule.dto;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public ServiceException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
