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
        // 유저와 예약을 매핑한다
        User user = User.builder().name("testUserName").build();

        Reservation reservation1 = Reservation.builder().build();
        Reservation reservation2 = Reservation.builder().build();
        user.addReservations(List.of(reservation1, reservation2));

        // 호텔과 예약을 매핑한다.
        Hotel hotel = Hotel.builder().build()
                .addRooms(List.of(
                        Room.builder().build()
                                .addReservations(List.of(
                                    reservation1, reservation2
                                ))));

        // 리뷰를 등록한다.
        Review review1 = Review.builder().content("testReviewContent1").build();
        Review review2 = Review.builder().content("testReviewContent2").build();

        // when
        reservation1.setReview(review1);
        reservation2.setReview(review2);
        hotelRepository.save(hotel);
        userRepository.save(user);

        // JPQL로 호텔정보까지 한번에 들고온다(쿼리 확인)
        List<Review> reviews = reviewRepository.findTop2ByHotelId(hotel.getId());

        // then
        assertThat(reviews.size(), is(2));
        assertThat(reviews.get(0), hasProperty("content", is("testReviewContent1")));
        assertThat(reviews.get(1), hasProperty("content", is("testReviewContent2")));
    }
}