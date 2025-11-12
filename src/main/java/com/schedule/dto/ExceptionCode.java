package com.schedule.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {     //Exception Notion (예외 enum)

    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "없는 유저입니다"),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다"),
    NOT_FOUND_SCHEDULE(HttpStatus.BAD_REQUEST, "찾으시는 일정이없습니다"),
    UN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "이메일과 비밀번호가 일치하지 않습니다");


    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
