package com.programmers.yogijogi.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private int rating;

    // 연관관계
    @OneToOne(mappedBy = "review")
    private Reservation reservation;

    // 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    @Builder
    public Review(String content, int rating, Reservation reservation, Hotel hotel) {
        this.content = content;
        this.rating = rating;
        this.reservation = reservation;
        this.hotel = hotel;
        hotel.addReview(this);
        reservation.setReview(this);
    }
}
