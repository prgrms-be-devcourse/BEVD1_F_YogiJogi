package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.HotelDetailInRoomDto;
import com.programmers.yogijogi.entity.dto.ReservableRoomResponseDto;
import com.programmers.yogijogi.entity.dto.RoomDetailDto;
import org.springframework.stereotype.Controller;


@Controller
public class RoomConverter {

    public static ReservableRoomResponseDto of(Room room) {
        return ReservableRoomResponseDto.builder()
                .id(room.getId())
                .name(room.getName())
                .price(room.getPrice())
                .stock(room.getStock())
                .maxGuest(room.getMaxGuest())
                .imageResponseDto(ImageConverter.of(room.getImage()))
                .build();
    }

    // dto -> entity
    public Room convertRoom(RoomDetailDto roomDetailDto) {
        return Room.builder()
                .id(roomDetailDto.getId())
                .name(roomDetailDto.getName())
                .price(roomDetailDto.getPrice())
                .stock(roomDetailDto.getStock())
                .maxGuest(roomDetailDto.getMaxGuest())
                .hotel(this.convertHotel(roomDetailDto.getHoteldto()))
                .build();
    }

    public Hotel convertHotel(HotelDetailInRoomDto hotelDetailInRoomDto) {
        return Hotel.builder()
                .id(hotelDetailInRoomDto.getId())
                .name(hotelDetailInRoomDto.getName())
                .build();
    }

    // entity -> dto
    public RoomDetailDto convertRoomDto(Room room) {
        return RoomDetailDto.builder()
                .id(room.getId())
                .name(room.getName())
                .price(room.getPrice())
                .stock(room.getStock())
                .maxGuest(room.getMaxGuest())
                .hoteldto(this.convertHotelDto(room.getHotel()))
                .build();
    }

    public HotelDetailInRoomDto convertHotelDto(Hotel hotel) {
        return HotelDetailInRoomDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .build();
    }
}
