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
public class RoomController {

    private final RoomService roomService;

    @GetMapping("hotels/{hotelId}/rooms")
    public ResponseEntity<List<RoomDto>> findAllRoom(@PathVariable("hotelId") Long id,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) throws NotFoundException {
        List<RoomDto> findRooms = roomService.findAllByDate2(id, startDate, endDate);
        return ResponseEntity.ok(findRooms);
    }

    @GetMapping("hotels/{hotelId}/{roomId}")
    public ResponseEntity<RoomDto> findOneRoom(@PathVariable("hotelId") Long hotelId,
                                               @PathVariable("roomId") Long roomId,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate endDate) throws NotFoundException {
        RoomDto findRoom = roomService.findOneByDate(roomId, startDate, endDate);
        return ResponseEntity.ok(findRoom);
    }
}
