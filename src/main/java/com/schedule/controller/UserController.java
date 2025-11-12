package com.schedule.controller;

import com.schedule.dto.*;
import com.schedule.service.UserService;
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
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    /**
     * 유저 조회
     * @param userId 유저 고유 ID
     * @return ResponseEntity<GetUserResponse> (id, email, username)
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    /**
     * 유저 수정
     * @param userId 유저 고유 ID
     * @param request UpdateUserRequest (email, username, password, newPassword)
     * @return ResponseEntity<UpdateUserResponse> (id, email, username, createdAt, modifiedAt)
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> update(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    /**
     * 유저 삭제
     * @param userId 유저 고유 ID
     * @param request DeleteUserRequest (password)
     * @return ResponseEntity<Void> NO_CONTENT
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @RequestBody @Valid DeleteUserRequest request) {
        userService.delete(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 로그인 세션 설정
     * @param request LoginRequest (email, password)
     * @param session HttpSession 세션
     * @return ResponseEntity<LoginResponse> (id, email), OK
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        LoginResponse result = userService.login(request);
        SessionUser sessionUser = new SessionUser(result.getId(), result.getEmail());

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
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, HttpSession session) {

        if(sessionUser == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
