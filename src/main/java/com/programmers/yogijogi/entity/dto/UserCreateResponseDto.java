package com.programmers.yogijogi.entity.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateResponseDto {
    private Long id;
    public UserCreateResponseDto(Long id) {
        this.id = id;
    }
}
