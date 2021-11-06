package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.HotelDetailInRoomDto;
import com.programmers.yogijogi.entity.dto.ReservableRoomResponseDto;
import com.programmers.yogijogi.entity.dto.RoomDetailResponseDto;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.stream.Collectors;


@Controller
public class RoomConverter {

    public static ReservableRoomResponseDto of(Room room) {
        ReservableRoomResponseDto dto = ReservableRoomResponseDto.builder()
                .id(room.getId())
                .name(room.getName())
                .price(room.getPrice())
                .stock(room.getStock())
                .maxGuest(room.getMaxGuest())
                .build();

        if(room.getImages().size() > 0) {
            dto.setImageResponseDto(ImageConverter.of(room.getImages().get(0)));
        }

        return dto;
    }

    // dto -> entity
    public Room convertRoom(RoomDetailResponseDto roomDetailResponseDto) {
        return Room.builder()
                .id(roomDetailResponseDto.getId())
                .name(roomDetailResponseDto.getName())
                .price(roomDetailResponseDto.getPrice())
                .stock(roomDetailResponseDto.getStock())
                .maxGuest(roomDetailResponseDto.getMaxGuest())
                .hotel(this.convertHotel(roomDetailResponseDto.getHoteldto()))
                .build();
    }

    public Hotel convertHotel(HotelDetailInRoomDto hotelDetailInRoomDto) {
        return Hotel.builder()
                .id(hotelDetailInRoomDto.getId())
                .name(hotelDetailInRoomDto.getName())
                .build();
    }

    // entity -> dto
    public RoomDetailResponseDto convertRoomDto(Room room) {
        return RoomDetailResponseDto.builder()
                .id(room.getId())
                .name(room.getName())
                .price(room.getPrice())
                .stock(room.getStock())
                .maxGuest(room.getMaxGuest())
                .imageResponseDtos(
                        room.getImages().stream().map(ImageConverter::of).collect(Collectors.toList()))
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
