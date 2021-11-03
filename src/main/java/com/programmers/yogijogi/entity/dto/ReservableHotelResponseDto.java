package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Image;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservableHotelResponseDto {
  Long id;
  String name;
  Double reviewAverage;
  Integer reviewCnt;
  Integer price;
  List<Image> images;
}
