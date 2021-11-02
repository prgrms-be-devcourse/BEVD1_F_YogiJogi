package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
public class ReservationRequestDto {
//    private LocalDate checkIn;
//    private LocalDate checkOut;
//    private Long roomId;
    private ReservationUserDto reservationUserDto;

    public ReservationRequestDto(ReservationUserDto reservationUserDto) {
        this.reservationUserDto = reservationUserDto;
    }
}