package com.programmers.yogijogi.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class HotelTest {

    @Test
    @DisplayName("Hotel - Review 연관관계 편의 메서드 테스트")
    void addReview() {
        // given
        Hotel hotel = Hotel.builder()
                .name("testHotel")
                .build();

        Review review = Review.builder()
                .hotel(hotel)
                .content("testContent")
                .rating(5)
                .build();

        Review review2 = Review.builder()
                .hotel(hotel)
                .content("testContent")
                .rating(5)
                .build();

        // when
        hotel.addReview(review);
        hotel.addReview(review2);

        // then
        assertThat(hotel.getReviews(), contains(review, review2));
        assertThat(review.getHotel(), is(hotel));
        assertThat(review2.getHotel(), is(hotel));
    }

    @Test
    @DisplayName("Hotel - Room 연관관계 편의 메서드 테스트")
    void addRoom() {
        // given
        Hotel hotel = Hotel.builder()
                .name("testHotel")
                .build();

        Room room = Room.builder()
                .price(5000)
                .build();

        Room room2 = Room.builder()
                .price(5000)
                .build();

        // when
        hotel.addRoom(room);
        hotel.addRoom(room2);
        // then
        assertThat(hotel.getRooms(), contains(room, room2));
        assertThat(room.getHotel(), is(hotel));
        assertThat(room2.getHotel(), is(hotel));
    }
}