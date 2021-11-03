package com.programmers.yogijogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.yogijogi.entity.*;
import com.programmers.yogijogi.entity.dto.HotelCreateDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.UserRepository;
import com.programmers.yogijogi.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    Hotel hotel;

    Long hotelId;

    @BeforeEach
    void setUp() {
        HotelCreateDto hotelCreateDto = HotelCreateDto.builder()
                .name("testName")
                .grade(5)
                .province(Province.Seoul1.toString())
                .theme(Theme.PC.toString())
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

        User user = User.builder().build();
        Reservation reservation1 = Reservation.builder().build();
        Reservation reservation2 = Reservation.builder().build();

        user.addReservations(List.of(reservation1, reservation2));

        Hotel hotel = Hotel.builder().build()
                .addRooms(List.of(Room.builder().build()
                        .addReservations(List.of(
                                reservation1.setReview(Review.builder().build()),
                                reservation2.setReview(Review.builder().build())
                        ))));

        // when
        hotelRepository.save(hotel);
        userRepository.save(user);

        mockMvc.perform(get("/hotels/{id}/reviews", hotel.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getReservableHotelBy() throws Exception {
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
        // given : 호텔의 필수 필드인 province를 생략함
        HotelCreateDto hotelCreateDto = HotelCreateDto.builder()
                .name("testName")
                .grade(5)
                .theme(Theme.PC.toString())
                .build();

        // when

        // then
        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotelCreateDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    static final LocalDate TEST_BASE_DATE = LocalDate.of(2020, 1, 1);
    static final int TEST_PRICE = 50000;
    static final int TEST_MAX_GUEST = 4;
    static final int TEST_MAX_STOCK = 1;
    static final Image TEST_IMAGE = new Image("testURL");

    @Test
    @DisplayName("호텔 단건 조회시, 예약가능한 방을 보여줘야한다.")
    @Transactional
    void getReservableRooms() throws Exception {
        Hotel hotel = hotelRepository.findById(hotelId).get();
        hotel.addRooms(List.of(
                // 테스트 날짜에 예약 가능한 방
                Room.builder().name("testName1").price(TEST_PRICE).maxGuest(TEST_MAX_GUEST).stock(TEST_MAX_STOCK).image(TEST_IMAGE).build()
                        .addReservations(
                                List.of(Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(3)).build(),
                                        Reservation.builder().checkIn(TEST_BASE_DATE.plusDays(6)).checkOut(TEST_BASE_DATE.plusDays(7)).build())),

                // 테스트 날짜에 예약 불가능한 방
                Room.builder().name("testName2").price(TEST_PRICE).maxGuest(TEST_MAX_GUEST).stock(TEST_MAX_STOCK).image(TEST_IMAGE).build()
                        .addReservations(
                                List.of(Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(5)).build(),
                                        Reservation.builder().checkIn(TEST_BASE_DATE.plusDays(6)).checkOut(TEST_BASE_DATE.plusDays(7)).build()))
        ));

        mockMvc.perform(get("/hotels/{id}/rooms", hotelId)
                .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("startDate", TEST_BASE_DATE.plusDays(4).toString())
                        .queryParam("endDate", TEST_BASE_DATE.plusDays(5).toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }
}