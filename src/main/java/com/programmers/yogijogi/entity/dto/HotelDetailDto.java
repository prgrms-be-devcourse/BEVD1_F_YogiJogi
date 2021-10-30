package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Theme;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HotelDetailDto {

    private String name;
    private String region;
    private int grade;
    private int totalReviews;
    private String theme;
    private List<ImageResponseDto> imageResponseDtos;
}
