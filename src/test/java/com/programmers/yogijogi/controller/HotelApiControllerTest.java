package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.entity.*;
import com.programmers.yogijogi.entity.dto.HotelCreateDto;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.UserRepository;
import com.programmers.yogijogi.service.HotelService;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.io.File;
import java.io.FileInputStream;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HotelApiControllerTest {

    @Value("${property.test.imageSource}")
    private String IMAGE_SOURCE;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    Long hotelId;

    @BeforeEach
    void setUp() {
        HotelCreateDto hotelCreateDto = HotelCreateDto.builder()
                .name("testName")
                .grade(5)
                .region("Seocho")
                .theme("pc")
                .build();

        hotelId = hotelService.save(hotelCreateDto);
    }

    @Test
    @DisplayName("호텔을 저장할 수 있어야한다.")
    void create() throws Exception {
        HotelCreateDto hotelCreateDto = HotelCreateDto.builder()
                .name("testName")
                .province(Province.Seoul1.toString())
                .grade(5)
                .theme(Theme.PC.toString())
                .build();

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelCreateDto)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("단일 호텔을 조회할 수 있어야한다.")
    void getOne() throws Exception {
        mockMvc.perform(get("/hotels/{hotelId}", hotelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @Test
//    @DisplayName("호텔 이미지를 등록해야 한다.")
//    void uploadHotelImage() throws Exception {
//        File f = new File(IMAGE_SOURCE);
//        FileInputStream fis = new FileInputStream(f);
//
//        MockMultipartFile multipartFile = new MockMultipartFile("images", f.getName(), "multipart/form-data", fis);
//        mockMvc.perform(fileUpload("/hotels/{id}/images", hotelId)
//                        .file(multipartFile)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }

    @Test
    @DisplayName("호텔의 이미지를 조회할 수 있어야한다.")
    void getAllImageByHotelId() throws Exception {
        mockMvc.perform(get("/hotels/{id}/images", hotelId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("호텔의 리뷰를 2개만 가져올 수 있어야한다.")
    void getTwoReviewsByHotelId() throws Exception {
        // given
        // 호텔과 룸을 먼저 만든다.
        Hotel hotel = Hotel.builder()
                .name("testHotelName")
                .build();

        Room room = Room.builder()
                .hotel(hotel)
                .name("testRoomName")
                .price(3000)
                .build();

        hotel.addRoom(room);

        // 유저와 룸에 예약 객체를 넣어준다.
        User user = User.builder()
                .name("testUserName")
                .build();

        Reservation reservation1 = Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();

        Reservation reservation2 = Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();

        // 리뷰를 등록한다.
        Review review1 = Review.builder()
                .hotel(hotel)
                .reservation(reservation1)
                .content("testReviewContent1")
                .rating(5)
                .build();

        Review review2 = Review.builder()
                .hotel(hotel)
                .reservation(reservation2)
                .content("testReviewContent2")
                .rating(5)
                .build();

        // when
        hotelRepository.save(hotel);
        userRepository.save(user);

        mockMvc.perform(get("/hotels/{id}/reviews", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getReservableHotelBy() throws Exception{
        LocalDate checkInTime = LocalDate.now();
        LocalDate checkOutTime = LocalDate.now().plusDays(7);

        mockMvc.perform(get("/hotels", hotelId)
                .queryParam("province", Province.Seoul1.toString())
                .queryParam("checkIn", checkInTime.toString())
                .queryParam("checkOut", checkOutTime.toString())
                .queryParam("guestCnt", String.valueOf(2))
                .queryParam("hotelGrade", String.valueOf(1))
                .queryParam("themes", Theme.WITH_PET.toString())
                .queryParam("themes", Theme.BBQ.toString())
                .queryParam("themes", Theme.SPA.toString())
                .queryParam("themes", Theme.SWIMMING_POOL.toString())
                .queryParam("themes", Theme.TERRACE.toString())

                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("호텔 생성 요청 객체 파라미터 테스트")
    void hotelCreateDtoParameterExceptionTest() throws Exception {
        // given : 호텔의 필수 필드인 Region을 생략함
        HotelCreateDto hotelCreateDto = HotelCreateDto.builder()
                .name("testName")
                .grade(5)
                .theme("pc")
                .build();

        // when

        // then
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelCreateDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}