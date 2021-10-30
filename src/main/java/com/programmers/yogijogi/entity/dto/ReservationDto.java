package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
