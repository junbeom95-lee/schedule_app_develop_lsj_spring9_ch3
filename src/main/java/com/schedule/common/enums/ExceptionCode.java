package com.schedule.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {     //Exception Notion (예외 enum)

    //일정
    NOT_FOUND_SCHEDULE(HttpStatus.BAD_REQUEST, "찾으시는 일정이없습니다"),

    //유저
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "없는 유저입니다"),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "존재하는 이메일입니다"),
    UN_AUTHORIZED(HttpStatus.UNAUTHORIZED, "이메일과 비밀번호가 일치하지 않습니다"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "잘못된 접근입니다"),

    //댓글
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "댓글이 존재하지 않습니다");

    private final HttpStatus status;
    private final String message;

    ExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
