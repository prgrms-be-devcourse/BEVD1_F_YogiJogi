package com.programmers.yogijogi.entity.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private RoomDto roomdto;

}
