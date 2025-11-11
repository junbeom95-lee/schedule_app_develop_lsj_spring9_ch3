package com.schedule.controller;

import com.schedule.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //TODO 유저 생성 create()
    //TODO Method : POST, URL : "/users"
    //TODO RequestBody CreateUserRequest (username, email)
    //TODO ResponseEntity<CreateUserResponse> (id, username, email), CREATED

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
