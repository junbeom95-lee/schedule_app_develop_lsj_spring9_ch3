package com.schedule.service;

import com.schedule.dto.CreateUserRequest;
import com.schedule.dto.CreateUserResponse;
import com.schedule.dto.GetUserResponse;
import com.schedule.entity.User;
import com.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 유저 생성
     * @param request CreateUserRequest (username, email)
     * @return CreateUserResponse (id, username, email)
     */
    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {

        //1. RequestDTO -> Entity
        User user = new User(request.getUsername(), request.getEmail());

        //2. Entity를 저장하고 영속화된 Entity
        User savedUser = userRepository.save(user);

        //3. Entity -> ResponseDTO
        return new CreateUserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    /**
     * 유저 조회
     * @param id 유저 고유 ID
     * @return GetUserResponse (id, username, email)
     */
    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long id) {

        //1. 유저 아이디 조회
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("없는 유저입니다"));

        //2. Response DTO 변환 및 반환
        return new GetUserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    //TODO 유저 수정 update()
    //TODO Param (Long id, UpdateUserRequest((username, email)))
    //TODO Return UpdateUserResponse (id, username, email)

    //TODO 유저 삭제 delete()
    //TODO Param (Long id)
    //TODO Return void
}
