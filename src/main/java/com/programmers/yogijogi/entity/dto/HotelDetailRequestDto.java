package com.programmers.yogijogi.entity.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class HotelDetailRequestDto {
    private LocalDate checkIn;
    private LocalDate checkOut;
}
