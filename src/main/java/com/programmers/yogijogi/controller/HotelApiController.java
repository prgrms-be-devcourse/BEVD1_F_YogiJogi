package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.common.S3Uploader;
import com.programmers.yogijogi.entity.Image;
import com.programmers.yogijogi.entity.dto.HotelDetailDto;
import com.programmers.yogijogi.entity.dto.ImageResponseDto;
import com.programmers.yogijogi.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/hotels")
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
            @PathVariable("id") Long hotelId) throws IOException
    {
        String url = s3Uploader.upload(multipartFile, HOTEL_DIRNAME + "/" + hotelId);
        hotelService.saveHotelImageUrl(hotelId, url);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<Page<ImageResponseDto>> getAllImageByHotelId(
            @PathVariable(value = "id") Long id,
            Pageable pageable)
    {
        return ResponseEntity.ok(hotelService.getImageByHotelId(id, pageable));
    }
}
