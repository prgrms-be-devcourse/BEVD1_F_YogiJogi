package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.common.S3Uploader;
import com.programmers.yogijogi.entity.Province;
import com.programmers.yogijogi.entity.Theme;
import com.programmers.yogijogi.entity.dto.HotelDetailDto;
import com.programmers.yogijogi.entity.dto.ImageResponseDto;
import com.programmers.yogijogi.entity.dto.ReservableHotelRequestDto;
import com.programmers.yogijogi.entity.dto.ReservableHotelResponseDto;
import com.programmers.yogijogi.service.HotelService;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/hotels")
@Slf4j
public class HotelApiController {

  public final String HOTEL_DIRNAME = "/hotels";

  @Autowired
  HotelService hotelService;

  @Autowired
  S3Uploader s3Uploader;

  @GetMapping("/{id}")
  public ResponseEntity<HotelDetailDto> getOne(@PathVariable Long id) {
    return ResponseEntity.ok(hotelService.getOne(id));
  }

  @PostMapping("/{id}/images")
  public ResponseEntity<String> uploadHotelImage(
      @RequestParam("images") MultipartFile multipartFile,
      @PathVariable("id") Long hotelId) throws IOException {
    String url = s3Uploader.upload(multipartFile, HOTEL_DIRNAME + "/" + hotelId);
    hotelService.saveHotelImageUrl(hotelId, url);
    return ResponseEntity.ok(url);
  }

  @GetMapping("/{id}/images")
  public ResponseEntity<Page<ImageResponseDto>> getAllImageByHotelId(
      @PathVariable(value = "id") Long id,
      Pageable pageable) {
    return ResponseEntity.ok(hotelService.getImageByHotelId(id, pageable));
  }

  // Validation Logic은 아직 추가하지 않음.
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

    if(Objects.nonNull(hotelGrade)){
      reservableHotelRequestDto.addCondition(hotelGrade);
    }

    if(Objects.nonNull(themes)){
      reservableHotelRequestDto.addCondition(themes);
    }

    List<ReservableHotelResponseDto> results = this.hotelService.getReservableHotelsBy(reservableHotelRequestDto);

    return ResponseEntity.ok(results);
  }
}
