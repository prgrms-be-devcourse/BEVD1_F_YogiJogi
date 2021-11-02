package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.ReservationConverter;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationConverter reservationConverter;

    @Transactional
    public Long save(ReservationRequestDto reservationRequestDto, Long roomId, LocalDate checkIn,LocalDate chekOut) {
        Reservation reservation = reservationRepository.save(reservationConverter
                .convertReservation(reservationRequestDto,roomId,checkIn,chekOut));
        return reservation.getId();
    }

    public ReservationResponseDto findId(Long reservationId) {
        Reservation reservation =reservationRepository.getById(reservationId);
        return reservationConverter.convertReservationResponseDto(reservation);
    }
}
