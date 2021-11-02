package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.ReservationConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationUserDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@Transactional
class ReservationServiceTest {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationRepository reservationRepository;
    Long savedHotelId;
    Long savedRoomId1;


    @Test
    void save_test() {
        Hotel hotel1 = Hotel.builder()
                .name("신라스테이")
                .build();

        Room room1 = Room.builder()
                .name("디럭스룸")
                .price(70000)
                .stock(1)
                .maxGuest(2)
                .hotel(hotel1)
                .build();

        savedHotelId = hotelRepository.save(hotel1).getId();
        savedRoomId1 = roomRepository.save(room1).getId();

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
                .reservationUserDto(
                        ReservationUserDto.builder().name("chaeWon").build()
                )
                .build();

        Long savedReservationId =  reservationService.save(reservationRequestDto,savedRoomId1,checkIn,checkOut);
        Reservation findReservation = reservationRepository.getById(savedReservationId);
        Assertions.assertThat(savedReservationId).isEqualTo(findReservation.getId());

    }
}