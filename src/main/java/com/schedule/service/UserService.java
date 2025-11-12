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

    /**
     * 유저 생성
     * @param request CreateUserRequest (email, username)
     * @return CreateUserResponse (id, email, username)
     */
    @Transactional
    public CreateUserResponse create(CreateUserRequest request) {

        boolean existence = userRepository.existsByEmail(request.getEmail());

        if (existence) throw new IllegalStateException("존재하는 이메일입니다");

        //1. RequestDTO -> Entity
        User user = new User(request.getEmail(), request.getUsername(), request.getPassword());

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

    /**
     * 유저 수정
     * @param userId 유저 고유 ID
     * @param request UpdateUserRequest (email, username, password, newPassword)
     * @return UpdateUserResponse (id, email, username, createdAt, modifiedAt)
     */
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {

        //1. 유저 아이디 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다"));

        //2. 비밀번호 일치하는지 확인 일치하지 않으면 throw
        if (!user.getPassword().equals(request.getPassword())) throw new UnauthorizedException("비밀번호가 일치하지 않습니다");

        //3. 영속성 컨텍스트를 활용하여 Entity 변경하여 자동으로 DB 반영
        user.update(request.getEmail(), request.getUsername(), request.getNewPassword());

        //4. Response DTO 변환 및 반환
        return new UpdateUserResponse(user.getId(), user.getEmail(), user.getUsername(), user.getCreatedAt(), user.getModifiedAt());
    }

    /**
     * 유저 삭제
     * @param userId 유저 고유 ID
     * @param request DeleteUserRequest (password)
     */
    @Transactional
    public void delete(Long userId, DeleteUserRequest request) {

        //1. 유저 비밀번호를 확인하기 위해 조회
        User savedUser = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("없는 유저입니다"));

        //2. 비밀번호가 틀리면 throw 맞으면 삭제
        if (!savedUser.getPassword().equals(request.getPassword())) throw new UnauthorizedException("비밀번호가 일치하지 않습니다");

        //3. 있으면 삭제
        userRepository.deleteById(userId);
    }

    /**
     * 로그인 (이메일, 비밀번호 확인)
     * @param request LoginRequest (email, password)
     * @return LoginResponse (id, email)
     */
    @Transactional
    public LoginResponse login(LoginRequest request) {

        //1. 유저 아이디로 비밀번호도 확인하기 위해 조회
        User savedUser = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalStateException("없는 유저입니다"));

        //2. 비밀번호가 틀리면 throw
        if (!savedUser.getPassword().equals(request.getPassword())) throw new UnauthorizedException("비밀번호가 일치하지 않습니다");

        //3. 이메일과 비밀번호가 일치하면 응답 DTO로 반환
        return new LoginResponse(savedUser.getId(), savedUser.getEmail());
    }
}
