package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.UserCreateRequestDto;
import com.programmers.yogijogi.entity.dto.UserDetailDto;
import com.programmers.yogijogi.entity.dto.UserUpdateRequestDto;
import com.programmers.yogijogi.entity.dto.UserUpdateResponseDto;
import org.springframework.stereotype.Controller;

@Controller
public class UserConverter {
    // dto -> entity
    public User convertUser(UserCreateRequestDto userCreateRequestDto) {
        return User.builder()
                .name(userCreateRequestDto.getName())
                .build();
    }

    //entity -> dto
    public UserCreateRequestDto convertUserCreateRequestDto(User user) {
        return UserCreateRequestDto.builder()
                .name(user.getName())
                .build();
    }

    public UserUpdateResponseDto convertUserUpdateResponseDto(User user) {
        return UserUpdateResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public UserDetailDto convertUserDetailDto(User user) {
        return UserDetailDto.builder()
                .name(user.getName())
                .build();
    }
}
