package com.schedule.controller;

import com.schedule.dto.CreateUserRequest;
import com.schedule.dto.CreateUserResponse;
import com.schedule.dto.GetUserResponse;
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
     * @param request CreateUserRequest (username, email)
     * @return ResponseEntity<CreateUserResponse> (id, username, email), CREATED
     */
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> create(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    /**
     * 유저 조회
     * @param userId 유저 고유 ID
     * @return ResponseEntity<GetUserResponse> (id, username, email)
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }

    //TODO 유저 수정 update()
    //TODO Method : PUT,  URL : "/users/{userId}"
    //TODO PathVariable (Long userId)
    //TODO RequestBody UpdateUserRequest (username, email)
    //TODO ResponseEntity<UpdateUserResponse> (id, username, email), OK

    //TODO 유저 삭제 delete()
    //TODO Method : DELETE,  URL : "/users/{userId}"
    //TODO PathVariable (Long userId)
    //TODO ResponseEntity<Void> NO_CONTENT
}
