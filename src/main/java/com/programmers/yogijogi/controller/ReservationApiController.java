package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
public class ReservationApiController {

    private final ReservationService reservationService;

    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/orders/{roomId}")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @PathVariable("roomId") Long roomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate checkOut,
            @Valid @RequestBody ReservationRequestDto reservationRequestDto) {
        Long reservationId = reservationService.save(reservationRequestDto,roomId,checkIn,checkOut);
        ReservationResponseDto reservationResponseDto = reservationService.findId(reservationId);
        return ResponseEntity.ok(reservationResponseDto);
    }


    @GetMapping("hotels/{userId}/reservations")
    public ResponseEntity<List<ReservationResponseDto>> findReservationByUserId(
            @PathVariable(value = "userId") Long userId) {
        List<ReservationResponseDto> reservationResponseDtos = reservationService.findByUserId(userId);
        return ResponseEntity.ok(reservationResponseDtos);
    }

    @DeleteMapping("orders/{reservationId}")
    public ResponseEntity<Long> deleteReservationByReservationId(
            @PathVariable(value = "reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
