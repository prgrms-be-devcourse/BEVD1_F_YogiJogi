package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT review " +
            "FROM Review review " +
            "join fetch review.reservation reservation " +
            "join fetch reservation.user where review.hotel.id = ?1")
    List<Review> findTop2ByHotelId(Long hotelId);
}
