package com.programmers.yogijogi.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponseDto {

    private String userName;
    private String content;
    private int rating;
}
