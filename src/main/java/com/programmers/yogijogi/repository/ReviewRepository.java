package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
