package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;


@RestController
public class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("hotels/{hotelId}/{roomId}/order")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @PathVariable("hotelId") Long hotelId,
            @PathVariable("roomId") Long roomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate chekIn,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate chekOut,
            @Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        Long reservationId = reservationService.save(reservationRequestDto,roomId,chekIn,chekOut);
        ReservationResponseDto reservationResponseDto = reservationService.findId(reservationId);
        return ResponseEntity.ok(reservationResponseDto);
    }
}
