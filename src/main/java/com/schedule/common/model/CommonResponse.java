package com.schedule.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse<T> {

    private final int code;             //상태코드 값
    private final HttpStatus status;    //상태 코드
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T content;            //응답에 대한 내용

    public CommonResponse(HttpStatus status, T content) {
        this.code = status.value();
        this.status = status;
        this.content = content;
    }
}
