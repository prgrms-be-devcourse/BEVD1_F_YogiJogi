package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationUserDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import com.programmers.yogijogi.repository.UserRepository;
import com.programmers.yogijogi.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReservationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

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

    Long userId;
    Long hotelId;
    Long roomId;

    @Test
    void createReservationTest() throws Exception {
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

        hotelId = hotelRepository.save(hotel1).getId();
        roomId = roomRepository.save(room1).getId();

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder().name("chaeWon").build();

        mockMvc.perform(post("/orders/{roomId}", roomId)
                        .param("checkIn", String.valueOf(LocalDate.now().plusDays(1)))
                        .param("checkOut", String.valueOf(LocalDate.now().plusDays(4)))
                        .content(objectMapper.writeValueAsString(reservationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void getReservationdByUserId() throws Exception {
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

        userId = userRepository.save(user).getId();
        hotelId = hotelRepository.save(hotel1).getId();
        roomId = roomRepository.save(room1).getId();

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder().id(userId)
                .name("chaeWon").build();

        Long savedReservationId = reservationService.save(reservationRequestDto, roomId, checkIn, checkOut);

        mockMvc.perform(get("/hotels/{userId}/reservations", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}

