package com.schedule.service;

import com.schedule.dto.CreateUserRequest;
import com.schedule.dto.CreateUserResponse;
import com.schedule.entity.User;
import com.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 생성
     * @param request CreateUserRequest (username, email)
     * @return CreateUserResponse (id, username, email)
     */
    public CreateUserResponse create(CreateUserRequest request) {

        User user = new User(request.getUsername(), request.getEmail());

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

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
