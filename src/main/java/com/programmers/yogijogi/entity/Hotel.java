package com.programmers.yogijogi.entity;

import com.programmers.yogijogi.entity.dto.HotelDetailDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Builder
    public Hotel(String name, Region region, int grade, Theme theme) {
        this.name = name;
        this.region = region;
        this.grade = grade;
        this.theme = theme;
    }

    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Region region;

    // 성급
    @Column(name = "grade")
    private int grade;

    // 테마
    @Column(name = "theme")
    @Enumerated(EnumType.STRING)
    private Theme theme;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private final List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private final List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private final List<Room> rooms = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;

    void setHost(Host host) {
        if(Objects.nonNull(this.host)){
            this.host.getHotels().remove(this);
        }
        this.host = host;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void addRoom(Room room){
        this.rooms.add(room);
        room.setHotel(this);
    }

    public void addImage(Image image) {
        this.images.add(image);
        image.setHotel(this);
    }
}
