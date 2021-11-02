package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.entity.dto.ReservationUserDto;
import com.programmers.yogijogi.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class ReservationConverter {

    private final RoomRepository roomRepository;

    public ReservationConverter(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // dto -> entity
    public Reservation convertReservation(ReservationRequestDto reservationRequestDto, Long roomId, LocalDate chekIn, LocalDate chekOut) {
        return Reservation.builder()
                .checkOut(chekOut)
                .checkIn(chekIn)
                .user(this.convertUser(reservationRequestDto.getReservationUserDto()))
                .room(roomRepository.getById(roomId))
                .build();
    }

    // dto -> entity
    public User convertUser(ReservationUserDto reservationUserDto) {
        return User.builder()
                .name(reservationUserDto.getName())
                .build();

    }


    // entity -> dto
    public ReservationResponseDto convertReservationResponseDto(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .roomId(reservation.getRoom().getId())
                .name(reservation.getUser().getName())
                .build();
    }
}
