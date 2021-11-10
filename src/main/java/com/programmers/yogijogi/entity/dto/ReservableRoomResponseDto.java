package com.programmers.yogijogi.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservableRoomResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private int maxGuest;
    private ImageResponseDto imageResponseDto;

    public void setImageResponseDto(ImageResponseDto imageResponseDto) {
        this.imageResponseDto = imageResponseDto;
    }
}
