package com.schedule.service;

import com.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //TODO 유저 생성 create()
    //TODO Param CreateUserRequest (username, email)
    //TODO Return CreateUserResponse> , createdAt, modifiedAt)

    //TODO 유저 조회 getUser()
    //TODO Param (Long id)
    //TODO Return GetUserResponse (id, username, email)

    //TODO 유저 수정 update()
    //TODO Param (Long id, UpdateUserRequest((username, email)))
    //TODO Return UpdateUserResponse (id, username, email)

    //TODO 유저 삭제 delete()
    //TODO Param (Long id)
    //TODO Return void
}
