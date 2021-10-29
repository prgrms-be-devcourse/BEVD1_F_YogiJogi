package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("2개의 리뷰를 가져온다.")
    void findTop2ByHotelId() {
        // given
        // 호텔과 룸을 먼저 만든다.
        Hotel hotel = Hotel.builder()
                .name("testHotelName")
                .build();

        Room room = Room.builder()
                .name("testRoomName")
                .price(3000)
                .build();

        hotel.addRoom(room);

        // 유저와 룸에 예약 객체를 넣어준다.
        User user = User.builder()
                .name("testUserName")
                .build();

        Reservation reservation1 = Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();

        Reservation reservation2 = Reservation.builder()
                .user(user)
                .room(room)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now())
                .build();

        room.addReservation(reservation1);
        room.addReservation(reservation2);

        user.addReservation(reservation1);
        user.addReservation(reservation2);

        // 리뷰를 등록한다.
        Review review1 = Review.builder()
                .hotel(hotel)
                .reservation(reservation1)
                .content("testReviewContent1")
                .rating(5)
                .build();

        Review review2 = Review.builder()
                .hotel(hotel)
                .reservation(reservation2)
                .content("testReviewContent2")
                .rating(5)
                .build();

        reservation1.setReview(review1);
        reservation2.setReview(review2);

        hotel.addReview(review1);
        hotel.addReview(review2);

        // when
        hotelRepository.save(hotel);
        userRepository.save(user);
        List<Review> reviews = reviewRepository.findTop2ByHotelId(hotel.getId());

        // then
        assertThat(reviews.size(), is(2));
        assertThat(reviews.get(0), hasProperty("content", is("testReviewContent1")));
        assertThat(reviews.get(1), hasProperty("content", is("testReviewContent2")));
    }
}