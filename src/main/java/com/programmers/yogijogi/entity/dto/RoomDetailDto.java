package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailDto {
    private Long id;
    private String name;
    private int price;
    private HotelDto hoteldto;
    private int stock;
    private int maxGuest;
}
