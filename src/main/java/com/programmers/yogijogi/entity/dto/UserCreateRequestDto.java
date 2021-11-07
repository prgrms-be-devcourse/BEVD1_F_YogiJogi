package com.programmers.yogijogi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequestDto {
    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;
}
