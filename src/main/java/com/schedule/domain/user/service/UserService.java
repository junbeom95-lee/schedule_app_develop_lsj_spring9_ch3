package com.schedule.domain.user.service;

import com.schedule.common.config.PasswordEncoder;
import com.schedule.common.enums.ExceptionCode;
import com.schedule.common.exception.ServiceException;
import com.schedule.domain.user.model.dto.CreateUserResponse;
import com.schedule.domain.user.model.dto.GetUserResponse;
import com.schedule.domain.user.model.dto.LoginResponse;
import com.schedule.domain.user.model.dto.UpdateUserResponse;
import com.schedule.domain.user.model.request.CreateUserRequest;
import com.schedule.domain.user.model.request.DeleteUserRequest;
import com.schedule.domain.user.model.request.LoginRequest;
import com.schedule.domain.user.model.request.UpdateUserRequest;
import com.schedule.common.entity.User;
import com.schedule.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    /**
     * 유저 생성
     * @param request CreateUserRequest (email, username)
     * @return CreateUserResponse (id, email, username)
     */
    public CreateUserResponse create(CreateUserRequest request) {

        //1. 이메일 존재 여부 확인
        boolean existence = userRepository.existsByEmail(request.getEmail());

        //2. 이메일은 unique 특성으로 하나만 존재 -> 존재하면 throw
        if (existence) throw new ServiceException(ExceptionCode.EXIST_EMAIL);

        //3. RequestDTO -> Entity
        User user = new User(request.getEmail(), request.getUsername(), encoder.encode(request.getPassword()));

        //4. Entity를 저장하고 영속화된 Entity
        User savedUser = userRepository.save(user);

        //5. Entity -> ResponseDTO
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
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ExceptionCode.NOT_FOUND_USER));

        //2. Response DTO 변환 및 반환
        return new GetUserResponse(user.getId(), user.getEmail(), user.getUsername());
    }

    /**
     * 유저 수정
     * @param userId 유저 고유 ID
     * @param request UpdateUserRequest (email, username, password, newPassword)
     * @return UpdateUserResponse (id, email, username, createdAt, modifiedAt)
     */
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {

        //1. 유저 아이디 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ExceptionCode.NOT_FOUND_USER));

        //2. 비밀번호 일치하는지 확인 일치하지 않으면 throw
        boolean matchPassword = encoder.matches(request.getPassword(), user.getPassword());
        if (!matchPassword) throw new ServiceException(ExceptionCode.UN_AUTHORIZED);

        //3. 영속성 컨텍스트를 활용하여 Entity 변경하여 자동으로 DB 반영
        user.update(request.getEmail(), request.getUsername(), encoder.encode(request.getNewPassword()));

        //4. Response DTO 변환 및 반환
        return new UpdateUserResponse(user.getId(), user.getEmail(), user.getUsername(), user.getCreatedAt(), user.getModifiedAt());
    }

    /**
     * 유저 삭제
     * @param userId 유저 고유 ID
     * @param request DeleteUserRequest (password)
     */
    public void delete(Long userId, DeleteUserRequest request) {

        //1. 유저 비밀번호를 확인하기 위해 조회
        User savedUser = userRepository.findById(userId).orElseThrow(() -> new ServiceException(ExceptionCode.NOT_FOUND_USER));

        //2. 비밀번호가 틀리면 throw 맞으면 삭제
        boolean matchPassword = encoder.matches(request.getPassword(), savedUser.getPassword());
        if (!matchPassword) throw new ServiceException(ExceptionCode.UN_AUTHORIZED);

        //3. 있으면 삭제
        userRepository.deleteById(userId);
    }

    /**
     * 로그인 (이메일, 비밀번호 확인)
     * @param request LoginRequest (email, password)
     * @return LoginResponse (id, email)
     */
    public LoginResponse login(LoginRequest request) {

        //1. 유저 아이디로 비밀번호도 확인하기 위해 조회
        User savedUser = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ServiceException(ExceptionCode.NOT_FOUND_USER));

        //2. 비밀번호가 틀리면 throw
        boolean matchPassword = encoder.matches(request.getPassword(), savedUser.getPassword());
        if (!matchPassword) throw new ServiceException(ExceptionCode.UN_AUTHORIZED);

        //3. 이메일과 비밀번호가 일치하면 응답 DTO로 반환
        return new LoginResponse(savedUser.getId(), savedUser.getEmail());
    }
}
