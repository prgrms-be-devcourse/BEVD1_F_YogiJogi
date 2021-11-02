package com.programmers.yogijogi.entity.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ReservationUserDto {
    private String name;

    public ReservationUserDto(String name) {
        this.name = name;
    }
}
