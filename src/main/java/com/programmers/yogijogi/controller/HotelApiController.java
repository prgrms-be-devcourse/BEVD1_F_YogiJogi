package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.common.S3Uploader;
import com.programmers.yogijogi.entity.Province;
import com.programmers.yogijogi.entity.Theme;
import com.programmers.yogijogi.entity.dto.*;
import com.programmers.yogijogi.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/hotels")
@Slf4j
public class HotelApiController {

    public final String HOTEL_DIRNAME = "/hotels";

    @Autowired
    private HotelService hotelService;

    @Autowired
    private S3Uploader s3Uploader;

    // 지역에 맞는 호텔 조회
    @GetMapping
    public ResponseEntity<List<ReservableHotelResponseDto>> getReservableHotelBy(
            @RequestParam(value = "province") Province province,  // 지역 이름. -> hotel에 속하는 필드
            @RequestParam(value = "checkIn") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkIn,  // 체크인 날짜. -> reservation에 속하는 필드
            @RequestParam(value = "checkOut") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkOut,  // 체크인 날짜. -> reservation에 속하는 필드
            @RequestParam int guestCnt, // 숙박 인원. -> room에 속하는 필

            // 필수가 아닌 값들.
            @RequestParam(required = false) Integer hotelGrade,
            @RequestParam(required = false) Theme[] themes
    ) {

        ReservableHotelRequestDto reservableHotelRequestDto = new ReservableHotelRequestDto(
                province,
                checkIn,
                checkOut,
                guestCnt
        );

        if (Objects.nonNull(hotelGrade)) {
            reservableHotelRequestDto.addCondition(hotelGrade);
        }

        if (Objects.nonNull(themes)) {
            reservableHotelRequestDto.addCondition(themes);
        }

        List<ReservableHotelResponseDto> results = this.hotelService.getReservableHotelsBy(reservableHotelRequestDto);

        return ResponseEntity.ok(results);
    }

    // 호텔 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<HotelDetailResponseDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getOne(id));
    }

    // 호텔 생성
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody HotelCreateDto hotelCreateDto) {
        Long hotelId = hotelService.save(hotelCreateDto);
        return new ResponseEntity<>(hotelId, HttpStatus.CREATED);
    }

    // 호텔 이미지 업로드(s3)
    @PostMapping("/{id}/images")
    public ResponseEntity<String> uploadHotelImage(
            @RequestParam("images") MultipartFile multipartFile,
            @PathVariable("id") Long hotelId) throws IOException {
        String url = s3Uploader.upload(multipartFile, HOTEL_DIRNAME + "/" + hotelId);
        hotelService.saveHotelImageUrl(hotelId, url);
        return ResponseEntity.ok(url);
    }

    // 호텔 이미지 불러오기(Url)
    @GetMapping("/{id}/images")
    public ResponseEntity<Page<ImageResponseDto>> getAllImageByHotelId(
            @PathVariable(value = "id") Long id,
            Pageable pageable) {
        return ResponseEntity.ok(hotelService.getImageByHotelId(id, pageable));
    }

    // 호텔 단건 조회시 예약 가능한 룸을 불러오는 api
    @GetMapping("/{id}/rooms")
    public ResponseEntity<List<ReservableRoomResponseDto>> getReservableRooms(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate)
    {
        return ResponseEntity.ok(hotelService.getReservableRooms(id, startDate, endDate));
    }

    // 호텔 단건 조회시, 리뷰 불러오기(2개까지만 불러옴)
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getTwoReviews(@PathVariable(value = "id") Long id) {
        List<ReviewResponseDto> reviewResponseDtos = hotelService.getTwoReviewsByHotelId(id);
        return ResponseEntity.ok(reviewResponseDtos);
    }
}
