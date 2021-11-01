package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Review;
import com.programmers.yogijogi.entity.dto.ReviewResponseDto;

public class ReviewConverter {

    public static ReviewResponseDto of(Review review) {
        return ReviewResponseDto.builder()
                .userName(review.getReservation().getUser().getName())
                .content(review.getContent())
                .rating(review.getRating())
                .build();
    }
}
