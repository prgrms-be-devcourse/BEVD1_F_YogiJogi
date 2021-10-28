package com.programmers.yogijogi.entity;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        Reservation reservation = Reservation.builder()
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();

        Review review = Review.builder()
                .content("testContent")
                .rating(5)
                .build();

        // when
        reservation.setReview(review);

        // then
        assertThat(review.getReservation(), is(reservation));
    }
}