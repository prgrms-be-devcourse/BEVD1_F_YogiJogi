package com.programmers.yogijogi.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ReservationTest {

    @Test
    @DisplayName("Reservation - Review 의 연관관계 편의 메서드 테스트")
    void setReview() {
        // given
        Reservation reservation = Reservation.builder().build();
        Review review = Review.builder().build();

        // when
        reservation.setReview(review);

        // then
        assertThat(review.getReservation(), is(reservation));
        assertThat(reservation.getReview(), is(review));
    }
}