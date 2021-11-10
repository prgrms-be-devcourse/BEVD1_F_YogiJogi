package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.ReservationConverter;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@Transactional
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationConverter reservationConverter;

    public Long save(ReservationRequestDto reservationRequestDto, Long roomId, LocalDate checkIn, LocalDate chekOut) {
        Long userId = reservationRequestDto.getId();
        User user;
        if(Objects.nonNull(userId)) {
             user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        }
        else {
            user = userRepository.save(User.builder().name(reservationRequestDto.getName()).build());
        }

        Reservation reservation = reservationConverter.convertReservation(reservationRequestDto, roomId, checkIn, chekOut);
        reservation = reservationRepository.save(reservation);
        user.addReservation(reservation);
        return reservation.getId();
    }

    public ReservationResponseDto findId(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException(ErrorMessage.RESERVATION_NOT_FOUND));
        return reservationConverter.convertReservationResponseDto(reservation);
    }

    //userId 통해서 예약 조회
    public List<ReservationResponseDto> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        List<Reservation> reservations = reservationRepository.getAllByUser(user);
        return reservations.stream().map(reservationConverter::convertReservationResponseDto).collect(toList());
    }

    //soft delete
    public void deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException(ErrorMessage.RESERVATION_NOT_FOUND));
        reservationRepository.deleteById(reservationId);
    }
}
