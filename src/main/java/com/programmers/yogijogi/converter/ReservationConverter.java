package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.entity.dto.ReservationUserDto;
import com.programmers.yogijogi.repository.RoomRepository;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class ReservationConverter {

    private final RoomRepository roomRepository;

    public ReservationConverter(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // dto -> entity
    public Reservation convertReservation(ReservationRequestDto reservationRequestDto, Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return Reservation.builder()
                .checkOut(checkOut)
                .checkIn(checkIn)
                .user(this.convertUser(reservationRequestDto))
                .room(roomRepository.getById(roomId))
                .build();
    }

    // dto -> entity
    public User convertUser(ReservationRequestDto reservationRequestDto) {
        return User.builder()
                .id(reservationRequestDto.getId())
                .name(reservationRequestDto.getName())
                .build();

    }

    // entity -> dto
    public ReservationResponseDto convertReservationResponseDto(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .checkIn(reservation.getCheckIn())
                .checkOut(reservation.getCheckOut())
                .roomId(reservation.getRoom().getId())
                .reservationUserDto(this.convertReservationUserDto(reservation.getUser()))
                .build();
    }

    public ReservationUserDto convertReservationUserDto(User User) {
        return ReservationUserDto.builder()
                .id(User.getId())
                .name(User.getName())
                .build();
    }
}
