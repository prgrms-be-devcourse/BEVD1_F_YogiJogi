package com.programmers.yogijogi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class HotelDetailRequestDto {
    private LocalDate checkIn;
    private LocalDate checkOut;
}
