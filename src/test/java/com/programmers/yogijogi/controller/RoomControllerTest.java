package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.converter.RoomConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.RoomDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import com.programmers.yogijogi.repository.UserRepository;
import com.programmers.yogijogi.service.RoomService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService roomService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomConverter roomConverter;

    @Autowired
    private ReservationRepository reservationRepository;

    Long hotelId;
    Long savedRoomId1;
    Long savedRoomId2;

    @BeforeEach
    void save_test() throws NotFoundException {

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

        Room room2 = Room.builder()
                .name("룸룸")
                .price(70000)
                .stock(1)
                .maxGuest(2)
                .hotel(hotel1)
                .build();

        hotelId = hotelRepository.save(hotel1).getId();
        savedRoomId1 = roomRepository.save(room1).getId();
        savedRoomId2 = roomRepository.save(room2).getId();
    }

    @Test
    void getRooms() throws Exception {

        User user = User.builder()
                .name("testUserName")
                .build();

        RoomDto findRoom = roomService.findOne(savedRoomId1);
        Reservation reservation1 = Reservation.builder()
                .user(user)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        userRepository.save(user);
        reservationRepository.save(reservation1);

        mockMvc.perform(get("/hotels/{hotelId}/rooms", hotelId)
                  .param("startDate", String.valueOf(LocalDate.now().plusDays(1)))
                  .param("endDate", String.valueOf(LocalDate.now().plusDays(4)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}