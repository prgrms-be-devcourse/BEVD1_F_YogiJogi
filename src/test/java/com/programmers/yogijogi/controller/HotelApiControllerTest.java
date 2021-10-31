package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.entity.*;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.service.HotelService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import org.springframework.util.MultiValueMap;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class HotelApiControllerTest {

    @Value("${property.test.imageSource}")
    private String IMAGE_SOURCE;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    Hotel hotel;

    Long hotelId;

    @BeforeAll
    void setUp() {
        hotel = Hotel.builder()
                .name("testName")
                .province(Province.Seoul1)
                .grade(5)
                .theme(Theme.PC)
                .build();

        hotel = hotelRepository.save(hotel);
        hotelId = hotel.getId();
    }

    @Test
    @DisplayName("단일 호텔을 조회할 수 있어야한다.")
    void getOneTest() throws Exception {
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
}