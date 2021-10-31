package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Province;
import com.programmers.yogijogi.entity.Theme;
import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReservableHotelRequestDto {

  // 아래의 4가지 요소는 필수적인 필드입니다.
  // 4가지 필드를 받지 못한 이상 의미가 없어지기 때문에 빌더를 사용하지 않았습니다.
  final Province province;
  final LocalDate checkIn;
  final LocalDate checkOut;
  final int guestCnt;

  // 아래의 2가지 요소는 필수적인 필드가 아닙니다.
  Theme[] themes;
  Integer hotelGrade;

  // 추가적인 검색 조건을 추가해주기 위한 메소드입니다. 메소드 체이닝을 사용할 수 있도록 아래와 같이 작성하였습니다.
  public ReservableHotelRequestDto addCondition(Integer hotelGrade) {
    this.hotelGrade = hotelGrade;
    return this;
  }

  // 추가적인 검색 조건을 추가해주기 위한 메소드입니다. 메소드 체이닝을 사용할 수 있도록 아래와 같이 작성하였습니다.
  public ReservableHotelRequestDto addCondition(Theme[] themes) {
    this.themes = themes;
    return this;
  }

}
