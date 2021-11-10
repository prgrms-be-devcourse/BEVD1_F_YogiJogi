package com.programmers.yogijogi.entity.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailResponseDto {
    private Long id;
    private String name;
    private int price;
    private HotelDetailInRoomDto hoteldto;
    private int stock;
    private int maxGuest;
    List<ImageResponseDto> imageResponseDtos;
}
