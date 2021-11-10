package com.programmers.yogijogi.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id @GeneratedValue
    Long id;

    @Column(name = "url")
    private String url;

    @Builder
    public Image(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    public void setHotel(Hotel hotel) {
        if(Objects.nonNull(this.hotel)) {
            this.hotel.getImages().remove(this);
        }
        this.hotel = hotel;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
