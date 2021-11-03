package com.programmers.yogijogi.entity.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ReservationUserDto {
    private Long id;
    private String name;

    public ReservationUserDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
