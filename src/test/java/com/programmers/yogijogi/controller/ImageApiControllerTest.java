//package com.programmers.yogijogi.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.programmers.yogijogi.entity.Hotel;
//import com.programmers.yogijogi.entity.Region;
//import com.programmers.yogijogi.entity.Theme;
//import com.programmers.yogijogi.repository.HotelRepository;
//import com.programmers.yogijogi.service.HotelService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.mock.web.MockPart;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.nio.ByteBuffer;
//
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@TestPropertySource("classpath:application-test.yaml")
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class ImageApiControllerTest {
//
//    @Autowired
//    private HotelService hotelService;
//
//    @Autowired
//    private HotelRepository hotelRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @DisplayName("호텔 이미지를 등록해야 한다.")
//    void uploadHotelImage() throws Exception {
//        Hotel hotel = Hotel.builder()
//                .name("testHotel")
//                .grade(5)
//                .region(Region.seoul)
//                .theme(Theme.PC)
//                .build();
//
//        hotel = hotelRepository.save(hotel);
//
//        File f = new File("src/test/resources/images/test.jpg");
//        FileInputStream fis = new FileInputStream(f);
//
//        MockMultipartFile multipartFile = new MockMultipartFile("images", f.getName(), "multipart/form-data", fis);
//        mockMvc.perform(fileUpload("/images/hotels")
//                .file(multipartFile)
//                .param("id", hotel.getId().toString()))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//}