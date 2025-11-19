package com.schedule.domain.user.controller;

import com.schedule.common.enums.ExceptionCode;
import com.schedule.common.exception.CustomException;
import com.schedule.common.model.CommonResponse;
import com.schedule.domain.user.model.dto.*;
import com.schedule.domain.user.model.request.CreateUserRequest;
import com.schedule.domain.user.model.request.DeleteUserRequest;
import com.schedule.domain.user.model.request.LoginRequest;
import com.schedule.domain.user.model.request.UpdateUserRequest;
import com.schedule.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 유저 생성
     * @param request CreateUserRequest (email, username, password)
     * @return ResponseEntity<CreateUserResponse> (id, email, username), CREATED
     */
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> create(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @RequestBody @Valid CreateUserRequest request) {

        //1. 로그인된 상태가 아니라면 회원 가입 가능
        if(sessionUser == null) {
            CommonResponse<?> result = userService.create(request);

            return ResponseEntity.status(result.getStatus()).body(result);
        }

        //2. 로그인 중이면 회원 가입 실패
        throw new CustomException(ExceptionCode.FORBIDDEN);
    }

    /**
     * 유저 조회
     * @param userId 유저 고유 ID
     * @return ResponseEntity<GetUserResponse> (id, email, username)
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<CommonResponse<?>> getUser(@PathVariable Long userId) {

        CommonResponse<?> result = userService.getUser(userId);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 유저 수정
     * @param userId 유저 고유 ID
     * @param request UpdateUserRequest (email, username, password, newPassword)
     * @return ResponseEntity<UpdateUserResponse> (id, email, username, createdAt, modifiedAt)
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<CommonResponse<?>> update(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request, HttpSession session) {

        //1. 유저 수정
        CommonResponse<UpdateUserResponse> result = userService.update(sessionUser, userId, request);

        //2. 유저 수정의 결과물을 꺼냄
        UpdateUserResponse updateUserResponse = result.getContent();

        //3. 유저 수정을 이룬 유저의 아이디와 이메일을 세션에 저장하려고 객체 생성
        SessionUser newSessionUser = new SessionUser(updateUserResponse.getId(), updateUserResponse.getEmail());

        //4. 세션에 수정된 유저의 정보를 설정
        session.setAttribute("loginUser", newSessionUser);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 유저 삭제
     * @param userId 유저 고유 ID
     * @param request DeleteUserRequest (password)
     * @return ResponseEntity<Void> NO_CONTENT
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<CommonResponse<?>> delete(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @PathVariable Long userId, @RequestBody @Valid DeleteUserRequest request, HttpSession session) {

        CommonResponse<?> result = userService.delete(sessionUser, userId, request);

        session.invalidate();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 로그인 세션 설정
     * @param request LoginRequest (email, password)
     * @param session HttpSession 세션
     * @return ResponseEntity<LoginResponse> (id, email), OK
     */
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<?>> login(@RequestBody @Valid LoginRequest request, HttpSession session) {

        //1. 로그인 로직을 통해 응답 DTO 생성
        CommonResponse<LoginResponse> result = userService.login(request);

        //2. 로그인 정보를 꺼냄
        LoginResponse loginResponse = result.getContent();

        //3. 로그인된 정보를 세션에 저장할 객체 생성
        SessionUser sessionUser = new SessionUser(loginResponse.getId(), loginResponse.getEmail());

        //4. 세션 설정
        session.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 로그아웃 세션 무효화
     * @param sessionUser "loginUser"키 값 DTO
     * @param session HttpSession 세션
     * @return ResponseEntity<Void>, NO_CONTENT
     */
    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<?>> logout(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session) {

        if(sessionUser == null) throw new CustomException(ExceptionCode.FORBIDDEN);

        //세션 무효화
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
