package com.programmers.yogijogi.entity;


import com.programmers.yogijogi.entity.dto.RoomDto;

import java.util.HashSet;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @OneToMany(mappedBy = "room")
    private final List<Reservation> reservations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @Column(name = "stock")
    private int stock;

    // 숙박 최대 인원
    @Column(name = "max_guest")
    private int maxGuest;

    @Builder
    public Room(Long id, String name, int price, Hotel hotel, int stock, int maxGuest) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.hotel = hotel;
        this.stock = stock;
        this.maxGuest = maxGuest;
    }

    public void setHotel(Hotel hotel) {
        if(Objects.nonNull(this.hotel)) {
            this.hotel.getRooms().remove(this);
        }
        this.hotel = hotel;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setRoom(this);
    }

    public void minusRoomStock() {
        this.stock = 0;
    }

    public void plusRoomStock() {
        this.stock = 1;
    }

    public Room addReservations(List<Reservation> reservations) {
        reservations.forEach(
                this::addReservation
        );
        return this;
    }
}
