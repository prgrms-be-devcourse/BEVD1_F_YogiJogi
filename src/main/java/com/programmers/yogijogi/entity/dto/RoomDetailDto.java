package com.programmers.yogijogi.entity.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailDto {
    private Long id;
    private String name;
    private int price;
    private HotelDetailInRoomDto hoteldto;
    private int stock;
    private int maxGuest;
}
