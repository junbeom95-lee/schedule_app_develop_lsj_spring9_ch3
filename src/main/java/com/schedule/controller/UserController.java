package com.schedule.controller;

import com.schedule.dto.*;
import com.schedule.service.UserService;
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
    public ResponseEntity<CreateUserResponse> create(@RequestBody CreateUserRequest request) {
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
    public ResponseEntity<UpdateUserResponse> update(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    /**
     * 유저 삭제
     * @param userId 유저 고유 ID
     * @param request DeleteUserRequest (password)
     * @return ResponseEntity<Void> NO_CONTENT
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @RequestBody DeleteUserRequest request) {
        userService.delete(userId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
