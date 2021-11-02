package com.programmers.yogijogi.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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
    public Hotel(Long id, String name, Host host, Province province, int grade, Theme theme) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.province = province;
        this.grade = grade;
        this.theme = theme;
    }

    @Column(name = "province")
    @Enumerated(EnumType.STRING)
    private Province province;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;

    void setHost(Host host) {
        if (Objects.nonNull(this.host)) {
            this.host.getHotels().remove(this);
        }
        this.host = host;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setHotel(this);
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        room.setHotel(this);
    }

    public Hotel addRooms(List<Room> rooms) {
        rooms.forEach(
                this::addRoom
        );
        return this;
    }

    public void addImage(Image image) {
        this.images.add(image);
        image.setHotel(this);
    }

    public Hotel addReviews(List<Review> reviews) {
        reviews.forEach(
                this::addReview
        );
        return this;
    }
}
