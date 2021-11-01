package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.RoomDto;
import com.programmers.yogijogi.service.RoomService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomApiController {

    private final RoomService roomService;

    //날짜 정보 쿼리 스트링으로 받아오는 방법
//    @GetMapping("hotels/{hotel-id}/{room-id}")
//    public BaseResponse<?> findAllByIdAndRegDateBetween(@RequestParam @DateTimeFormat(pattern = "yyy-MM-dd")LocalDate startDate,
//                                                        @RequestParam @DateTimeFormat(pattern = "yyy-MM-dd")LocalDate endDate){
//
//    }

//    @GetMapping("hotels/{hotelId}")
//    public ResponseEntity<List<RoomDto>> findAllRoom(@PathVariable("hotelId") Long id) {
//        log.info("id :: {}", id);
//        List<RoomDto> findRooms = roomService.findAllByHotelId(id);
////        findRooms.forEach(room -> log.info("room result :: {}", room.getHotel()));
////        result.forEach(roomDto -> log.info("result :: {} ", roomDto.getName()));
//        return ResponseEntity.ok(findRooms);
//    }

    @GetMapping("hotels/{hotelId}/rooms")
    public ResponseEntity<List<RoomDto>> findAllRoom(@PathVariable("hotelId") Long id,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) throws NotFoundException {
        List<RoomDto> findRooms = roomService.findAllByDate2(id, startDate, endDate);
        return ResponseEntity.ok(findRooms);
    }
}
