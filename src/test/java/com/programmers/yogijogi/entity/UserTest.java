package com.programmers.yogijogi.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserTest {

    @Test
    @DisplayName("User - Reservation 의 연관관계 편의 메서드 테스트")
    void addReservation() {
        // given
        User user = User.builder().build();

        Hotel hotel = Hotel.builder()
                .name("testHotel")
                .build();

        Room room = Room.builder()
                .name("testRoom")
                .hotel(hotel)
                .build();

        Reservation reservation = Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .build();

        Reservation reservation2= Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .build();

        // then
        assertThat(user.getReservations(), contains(reservation, reservation2));
        assertThat(reservation.getUser(), is(user));
        assertThat(reservation.getUser(), is(user));
    }
}