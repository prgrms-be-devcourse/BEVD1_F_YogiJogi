package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Review;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.HotelDetailResponseDto;
import com.programmers.yogijogi.entity.dto.ReservableHotelResponseDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HotelConverter {

    public static HotelDetailResponseDto of(Hotel hotel) {
        return HotelDetailResponseDto.builder()
                .name(hotel.getName())
                .grade(hotel.getGrade())
                .region(hotel.getProvince().toString())
                .theme(hotel.getTheme().toString())
                .reviewAverage(getReviewAverage(hotel.getReviews()))
                .totalReviews(hotel.getReviews().size())
                .imageResponseDtos(hotel.getImages().stream()
                        .map(image -> ImageConverter.of(image)).collect(Collectors.toList()))
                .build();
    }

    public static ReservableHotelResponseDto convertToReservableHotelResponseDto(Hotel hotel) {
        return ReservableHotelResponseDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .price(getCheapestRoomPrice(List.copyOf(hotel.getRooms())))
                .grade(hotel.getGrade())
                .reviewAverage(getReviewAverage(hotel.getReviews()))
                .reviewCnt(hotel.getReviews().size())
                .imageUrl(hotel.getImages().get(0).getUrl())
                .build();
    }

    public static double getReviewAverage(List<Review> reviews) {
        return reviews.stream()
                .map(Review::getRating)
                .mapToDouble(d -> d)
                .average()
                .orElse(0);
    }

    public static int getCheapestRoomPrice(List<Room> rooms) {
        if (Objects.isNull(rooms)) {
            return 0;
        }
        return rooms.stream()
                .mapToInt(Room::getPrice)
                .min()
                .orElse(0);
    }
}
