package com.programmers.yogijogi.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class RoomTest {

    @Test
    @DisplayName("Room - Reservation 의 연관관계 편의 메서드 테스트")
    void addReservation() {
        // given
        Room room = Room.builder().name("testRoom").build();

        Reservation reservation1 = Reservation.builder()
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .build();

        Reservation reservation2= Reservation.builder()
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .build();

        room.addReservations(List.of(reservation1, reservation2));
        // then
        assertThat(room.getReservations(), contains(reservation1, reservation2));
        assertThat(reservation1.getRoom(), is(room));
        assertThat(reservation2.getRoom(), is(room));
    }
}