package com.programmers.yogijogi.entity.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HotelDetailResponseDto {

    private String name;
    private String region;
    private int grade;
    private int totalReviews;
    private double reviewAverage;
    private String theme;
    private List<ImageResponseDto> imageResponseDtos;
}
