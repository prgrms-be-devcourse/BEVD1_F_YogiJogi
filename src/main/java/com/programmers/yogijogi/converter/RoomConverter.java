package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.HotelDto;
import com.programmers.yogijogi.entity.dto.ReservationDto;
import com.programmers.yogijogi.entity.dto.RoomDto;
import org.springframework.stereotype.Controller;


@Controller
public class RoomConverter {
    // dto -> entity
    public Room convertRoom(RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .name(roomDto.getName())
                .price(roomDto.getPrice())
                .stock(roomDto.getStock())
                .maxGuest(roomDto.getMaxGuest())
                .hotel(this.convertHotel(roomDto.getHoteldto()))
                .build();

    }

    public Hotel convertHotel(HotelDto hotelDto) {
        return Hotel.builder()
                .id(hotelDto.getId())
                .name(hotelDto.getName())
                .build();
    }

    public Reservation convertReservation(ReservationDto reservationDto) {
        return Reservation.builder()
                .id(reservationDto.getId())
                .checkOut(reservationDto.getCheckOut())
                .checkIn(reservationDto.getCheckIn())
                .room(this.convertRoom(reservationDto.getRoomdto()))
                .build();
    }


    // entity -> dto
    public RoomDto convertRoomDto(Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .name(room.getName())
                .price(room.getPrice())
                .stock(room.getStock())
                .maxGuest(room.getMaxGuest())
                .hoteldto(this.convertHotelDto(room.getHotel()))
                .build();
    }

    public HotelDto convertHotelDto(Hotel hotel) {
        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .build();
    }

    public ReservationDto convertReservationDto(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .checkOut(reservation.getCheckOut())
                .checkIn(reservation.getCheckIn())
                .roomdto(this.convertRoomDto(reservation.getRoom()))
                .build();
    }

}
