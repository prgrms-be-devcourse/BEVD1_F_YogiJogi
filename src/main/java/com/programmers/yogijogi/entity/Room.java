package com.programmers.yogijogi.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "room")
@NoArgsConstructor
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int price;

    //reservation_id //FK
    //hotel_id //FK

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @Builder
    public Room(int price, Hotel hotel) {
        this.price = price;
        this.hotel = hotel;
    }

    public void setHotel(Hotel hotel){
        if(Objects.nonNull(this.hotel)){
            this.hotel.getRooms().remove(this);
        }
        this.hotel = hotel;
    }

    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
        reservation.setRoom(this);
    }
}
