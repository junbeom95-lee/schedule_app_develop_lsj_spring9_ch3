package com.schedule.controller;

import com.schedule.dto.ErrorResponse;
import com.schedule.dto.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Service에서 일어난 예외 처리
     * @param e ServiceException (HttpStatus, message)
     * @return ErrorResponse (HttpStatus, message)
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> serviceException(ServiceException e) {

        ErrorResponse response = new ErrorResponse(e.getStatus(), e.getMessage());

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
