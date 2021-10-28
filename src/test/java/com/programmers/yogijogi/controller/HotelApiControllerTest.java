package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.entity.dto.HotelCreateDto;
import com.programmers.yogijogi.service.HotelService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class HotelApiControllerTest {

    @Value("${property.test.imageSource}")
    private String IMAGE_SOURCE;

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
                .grade(5)
                .region("Seocho")
                .theme("pc")
                .build();

        mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hotelCreateDto)))
                .andExpect(status().isCreated())
                .andDo(print());
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
}