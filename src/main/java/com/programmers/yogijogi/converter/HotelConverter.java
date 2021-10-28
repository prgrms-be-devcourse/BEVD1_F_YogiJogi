package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.dto.HotelDetailDto;

import java.util.stream.Collectors;

public class HotelConverter {

    public static HotelDetailDto of(Hotel hotel) {
        return HotelDetailDto.builder()
                .name(hotel.getName())
                .grade(hotel.getGrade())
                .region(hotel.getRegion().toString())
                .theme(hotel.getTheme().toString())
                .totalReviews(hotel.getReviews().size())
                .imageResponseDtos(hotel.getImages().stream()
                        .map(image -> ImageConverter.of(image)).collect(Collectors.toList()))
                .build();
    }
}
