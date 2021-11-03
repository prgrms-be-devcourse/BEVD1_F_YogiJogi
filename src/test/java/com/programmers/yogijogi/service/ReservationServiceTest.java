package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.ReservationConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.entity.dto.ReservationUserDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import com.programmers.yogijogi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
    @Autowired
    UserRepository userRepository;

    Long savedHotelId;
    Long savedRoomId1;
    Long savedUserId;


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

        User user = User.builder()
                .name("testUserName")
                .build();

        savedUserId = userRepository.save(user).getId();
        savedHotelId = hotelRepository.save(hotel1).getId();
        savedRoomId1 = roomRepository.save(room1).getId();

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
                .reservationUserDto(
                        ReservationUserDto.builder()
                                .id(savedUserId)
                                .name("chaeWon").build()
                )
                .build();

        Long savedReservationId =  reservationService.save(reservationRequestDto,savedRoomId1,checkIn,checkOut);
        Reservation findReservation = reservationRepository.getById(savedReservationId);
        Assertions.assertThat(savedReservationId).isEqualTo(findReservation.getId());

    }

    @Test
    @DisplayName("사용자는 예약정보를 확인 할 수 있어야한다. ")
    void findReservationByUserId(){

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

        User user = User.builder()
                .name("testUserName")
                .build();

        savedUserId = userRepository.save(user).getId();
        savedHotelId = hotelRepository.save(hotel1).getId();
        savedRoomId1 = roomRepository.save(room1).getId();

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
                .reservationUserDto(
                        ReservationUserDto.builder()
                                .id(savedUserId)
                                .name("chaeWon").build()
                )
                .build();

        Long savedReservationId =  reservationService.save(reservationRequestDto,savedRoomId1,checkIn,checkOut);
        List<ReservationResponseDto> reservationResponseDtos = reservationService.findByUserId(savedUserId);
        Assertions.assertThat(savedUserId).isEqualTo(reservationResponseDtos.get(0).getReservationUserDto().getId());
    }
}