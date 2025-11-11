package com.schedule.service;

import com.schedule.dto.*;
import com.schedule.entity.User;
import com.schedule.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //TODO 비밀번호 추가
    /**
     * 유저 생성
     * @param request CreateUserRequest (email, username)
     * @return CreateUserResponse (id, email, username)
     */
    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {

        //1. RequestDTO -> Entity
        User user = new User(request.getUsername(), request.getEmail());

        //2. Entity를 저장하고 영속화된 Entity
        User savedUser = userRepository.save(user);

        //3. Entity -> ResponseDTO
        return new CreateUserResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername());
    }

    /**
     * 유저 조회
     * @param userId 유저 고유 ID
     * @return GetUserResponse (id, email, username)
     */
    @Transactional(readOnly = true)
    public GetUserResponse getUser(Long userId) {

        //1. 유저 아이디 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다"));

        //2. Response DTO 변환 및 반환
        return new GetUserResponse(user.getId(), user.getEmail(), user.getUsername());
    }

    //TODO 비밀번호 맞으면 수정 아니면 throw
    /**
     * 유저 수정
     * @param userId 유저 고유 ID
     * @param request UpdateUserRequest (email, username)
     * @return UpdateUserResponse (id, email, username, createdAt, modifiedAt)
     */
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {

        //1. 유저 아이디 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다"));

        //2. 영속성 컨텍스트를 활용하여 Entity 변경하여 자동으로 DB 반영
        user.update(request.getUsername(), request.getEmail());

        //2. Response DTO 변환 및 반환
        return new UpdateUserResponse(user.getId(), user.getEmail(), user.getUsername(), user.getCreatedAt(), user.getModifiedAt());
    }

    //TODO 비밀번호 맞으면 삭제 가능
    /**
     * 유저 삭제
     * @param userId 유저 고유 ID
     */
    @Transactional
    public void delete(Long userId) {

        //1. 유저 아이디 존재 여부 확인
        boolean existence = userRepository.existsById(userId);

        //2. 없으면 throw
        if (!existence) throw new IllegalStateException("없는 유저입니다");

        //3. 있으면 삭제
        userRepository.deleteById(userId);
    }
}
