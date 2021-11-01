package com.programmers.yogijogi.entity;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ReservationTest {

    @Test
    @DisplayName("Reservation - Review 의 연관관계 편의 메서드 테스트")
    void setReview() {
        // given
        User user = User.builder().build();

        Hotel hotel = Hotel.builder()
                .name("testHotel")
                .build();

        Room room = Room.builder()
                .hotel(hotel)
                .build();

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();

        Review review = Review.builder()
                .hotel(hotel)
                .reservation(reservation)
                .content("testContent")
                .rating(5)
                .build();

        // then
        assertThat(review.getReservation(), is(reservation));
        assertThat(reservation.getReview(), is(review));
    }
}