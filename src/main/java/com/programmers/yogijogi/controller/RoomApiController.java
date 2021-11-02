package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.common.S3Uploader;
import com.programmers.yogijogi.entity.dto.RoomDetailDto;
import com.programmers.yogijogi.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final String ROOM_DIRNAME = "/rooms";

    private final RoomService roomService;
    private final S3Uploader s3Uploader;

//    @GetMapping("/hotels/{hotelId}/rooms")
//    public ResponseEntity<List<RoomDto>> findAllRoom(@PathVariable("hotelId") Long id,
//                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate startDate,
//                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) {
//        List<RoomDto> findRooms = roomService.findAllByDate2(id, startDate, endDate);
//        return ResponseEntity.ok(findRooms);
//    }

    // 룸 이미지 업로드(s3)
    @PostMapping("/rooms/{id}/images")
    public ResponseEntity<String> uploadRoomImage(
            @RequestParam("images") MultipartFile multipartFile,
            @PathVariable("id") Long roomId) throws IOException {
        String url = s3Uploader.upload(multipartFile, ROOM_DIRNAME + "/" + roomId);
        roomService.saveRoomImageUrl(roomId, url);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/hotels/{hotelId}/{roomId}")
    public ResponseEntity<RoomDetailDto> findOneRoom(@PathVariable("hotelId") Long hotelId,
                                                     @PathVariable("roomId") Long roomId,
                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) {
        RoomDetailDto findRoom = roomService.findOneByDate(roomId, startDate, endDate);
        return ResponseEntity.ok(findRoom);
    }
}
