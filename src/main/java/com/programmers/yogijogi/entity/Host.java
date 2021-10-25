package com.programmers.yogijogi.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//많이쓰이는쪽에 add 반대쪽은 set
@Entity
@Table(name = "host")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Host {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "host")
    private List<Hotel> hotels = new ArrayList<>();

    @Builder
    public Host(String name) {
        this.name = name;
    }

    void addHotel(Hotel hotel) {
        this.hotels.add(hotel);
        hotel.setHost(this);
    }

}
