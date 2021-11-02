package com.programmers.yogijogi.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Getter
@NoArgsConstructor
public class ReservationResponseDto {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long roomId;
    private String name;

    public ReservationResponseDto(Long id, LocalDate checkIn, LocalDate checkOut, Long roomId, String name) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomId = roomId;
        this.name = name;
    }
}
