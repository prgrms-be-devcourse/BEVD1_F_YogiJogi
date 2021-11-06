package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomCreateRequestDto {

    private String name;
    private int maxGuest;
    private int price;
    private int stock;

    public Room toEntity() {
        return Room.builder()
                .name(this.name)
                .maxGuest(this.maxGuest)
                .price(this.price)
                .stock(this.stock)
                .build();
    }
}
