package com.schedule.controller;

import com.schedule.dto.CreateUserRequest;
import com.schedule.dto.CreateUserResponse;
import com.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    //TODO 유저 조회 getUser()
    //TODO Method : GET,  URL : "/users/{id}"
    //TODO PathVariable (Long id)
    //TODO ResponseEntity<GetUserResponse> (id, username, email), OK

    //TODO 유저 수정 update()
    //TODO Method : PUT,  URL : "/users/{id}"
    //TODO PathVariable (Long id)
    //TODO RequestBody UpdateUserRequest (username, email)
    //TODO ResponseEntity<UpdateUserResponse> (id, username, email), OK

    //TODO 유저 삭제 delete()
    //TODO Method : DELETE,  URL : "/users/{id}"
    //TODO PathVariable (Long id)
    //TODO ResponseEntity<Void> NO_CONTENT
}
