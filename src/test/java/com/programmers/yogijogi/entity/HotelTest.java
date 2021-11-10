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
class HotelTest {

    @Test
    @DisplayName("Hotel - Review 연관관계 편의 메서드 테스트")
    void addReview() {
        // given
        User user = User.builder().build();

        Hotel hotel = Hotel.builder().build()
                .addRooms(
                        List.of(Room.builder().build().addReservations(
                                        List.of(Reservation.builder().build()))
                        ));

        Review review = Review.builder().build();
        Review review2 = Review.builder().build();

        hotel.addReviews(List.of(review, review2));

        // then
        assertThat(hotel.getReviews(), contains(review, review2));
        assertThat(review.getHotel(), is(hotel));
        assertThat(review2.getHotel(), is(hotel));
    }

    @Test
    @DisplayName("Hotel - Room 연관관계 편의 메서드 테스트")
    void addRoom() {
        // given
        Hotel hotel = Hotel.builder().build();
        Room room = Room.builder().build();
        Room room2 = Room.builder().build();

        // when
        hotel.addRooms(List.of(room, room2));

        // then
        assertThat(hotel.getRooms(), contains(room, room2));
        assertThat(room.getHotel(), is(hotel));
        assertThat(room2.getHotel(), is(hotel));
    }

    private static final int TEST_GUEST_CNT = 1;
    private static final LocalDate TEST_BASE_DATE = LocalDate.of(2020, 1, 1);

    @Test
    @DisplayName("Hotel - Room 연관관계 편의 메서드 테스트")
    void addRooms() {
        Hotel hotel = Hotel.builder().build()
                .addRooms(
                        List.of(
                                Room.builder().maxGuest(TEST_GUEST_CNT).build()
                                        .addReservations(List.of(
                                                Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                                        )),
                                Room.builder().maxGuest(TEST_GUEST_CNT).build()
                                        .addReservations(List.of(
                                                Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                                        )),
                                Room.builder().maxGuest(TEST_GUEST_CNT).build()
                                        .addReservations(List.of(
                                                Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                                        ))
                        )
                );

        assertThat(hotel.getRooms().size(), is(3));
        hotel.getRooms().forEach(
                room -> {
                    assertThat(room.getHotel(), is(hotel));
                }
        );
    }
}