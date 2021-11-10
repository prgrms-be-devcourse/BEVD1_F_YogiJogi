package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.UserConverter;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.*;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import com.programmers.yogijogi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
//@RequiredArgsConstructor
public class UserService {

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    /**
     * 회원가입
     */
    @Transactional
    public UserCreateResponseDto join(UserCreateRequestDto userCreateRequestDto) {
        User user = userConverter.convertUser(userCreateRequestDto);
        validateDuplicateMember(user); //중복 회원 검증
        User newUser = userRepository.save(user);
        return UserCreateResponseDto.builder()
                .id(newUser.getId())
                .build();
    }
    private void validateDuplicateMember(User user) {
        List<User> findMembers = userRepository.findByName(user.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<User> findMembers() {
        return userRepository.findAll();
    }

    //회원 단건 조회
    public UserDetailDto findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        return userConverter.convertUserDetailDto(user);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public UserUpdateResponseDto update(Long id, String name) {
        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        user.updateUser(name);

        return UserUpdateResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}