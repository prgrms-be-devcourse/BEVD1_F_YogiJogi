package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Province;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.Theme;
import com.programmers.yogijogi.entity.dto.ReservationRequestDto;
import com.programmers.yogijogi.entity.dto.ReservationResponseDto;
import com.programmers.yogijogi.entity.dto.ReservationUserDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import com.programmers.yogijogi.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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
    Long HotelId;
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

        HotelId = hotelRepository.save(hotel1).getId();
        roomId = roomRepository.save(room1).getId();

        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);

        ReservationRequestDto reservationRequestDto = ReservationRequestDto.builder()
                .reservationUserDto(
                        ReservationUserDto.builder().name("chaeWon").build()
                )
                .build();


//        mockMvc.perform(post("/hotels")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(hotelCreateDto)))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }

        mockMvc.perform(post("/hotels/{hotelId}/{roomId}/order",HotelId, roomId)
                        .param("chekIn", String.valueOf(LocalDate.now().plusDays(1)))
                        .param("chekOut", String.valueOf(LocalDate.now().plusDays(4)))
                        .content(objectMapper.writeValueAsString(reservationRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
//                .queryParam("checkIn", checkInTime.toString())
//                .queryParam("checkOut", checkOutTime.toString())

    }
}

