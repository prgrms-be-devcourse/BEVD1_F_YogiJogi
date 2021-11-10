package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.converter.RoomConverter;
import com.programmers.yogijogi.entity.*;
import com.programmers.yogijogi.entity.dto.RoomCreateRequestDto;
import com.programmers.yogijogi.entity.dto.RoomDetailResponseDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import com.programmers.yogijogi.repository.UserRepository;
import com.programmers.yogijogi.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class RoomApiControllerTest {

    @Value("${property.test.imageSource}")
    private String IMAGE_SOURCE;

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

        Room room2 = Room.builder()
                .name("룸룸")
                .price(70000)
                .stock(1)
                .maxGuest(2)
                .hotel(hotel1)
                .build();

        room1.addImage((new Image("testUrl")));
        room2.addImage((new Image("testUrl")));

        hotelId = hotelRepository.save(hotel1).getId();
        savedRoomId1 = roomRepository.save(room1).getId();
        savedRoomId2 = roomRepository.save(room2).getId();
    }

    @Test
    void findOneRoom() throws Exception {
        RoomDetailResponseDto findRoom = roomService.findOne(savedRoomId1);

        User user = User.builder()
                .name("testUserName")
                .build();

        Reservation reservation1 = Reservation.builder()
                .user(user)
                .checkIn(LocalDate.now().plusDays(10))
                .checkOut(LocalDate.now().plusDays(13))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        userRepository.save(user);
        reservationRepository.save(reservation1);

        mockMvc.perform(get("/hotels/{hotelId}/{roomId}", hotelId, savedRoomId1)
                        .param("checkIn", String.valueOf(LocalDate.now().plusDays(1)))
                        .param("checkOut", String.valueOf(LocalDate.now().plusDays(4)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @DisplayName("룸 이미지를 등록해야 한다.")
    void uploadRoomImage() throws Exception {
        File f = new File(IMAGE_SOURCE);
        FileInputStream fis = new FileInputStream(f);

        MockMultipartFile multipartFile = new MockMultipartFile("images", f.getName(), "multipart/form-data", fis);
        mockMvc.perform(fileUpload("/rooms/{id}/images", savedRoomId1)
                        .file(multipartFile)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("방을 생성할 수 있다.")
    void create() throws Exception {
        RoomCreateRequestDto dto = RoomCreateRequestDto.builder().build();


        mockMvc.perform(post("/hotels/{hotelId}/rooms", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}