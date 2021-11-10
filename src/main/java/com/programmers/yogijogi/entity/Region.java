package com.programmers.yogijogi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@JsonFormat(shape = Shape.OBJECT)
@RequiredArgsConstructor
public enum Region {
  // 서울
  Gangnam("강남"),
  Songpa("송파"),
  Seocho("서초"),

  // 서울
  MyeongDong("명동"),
  DongDaeMoon("동대문"),
  JongRo("종로"),

  // 서울
  Mapo("마포"),
  Yeongdeungpo("영등포"),
  Yongsan("용산"),

  // 강원
  Gangneung("강릉"),
  Sokcho("속초"),
  Yangyang("양양"),

  // 부산
  Haeundae("해운대"),
  Gwangalli("광안리"),
  Seomyeon("서면"),

  // 제주
  Seogwipo("서귀포"),
  Jeju("제주");

  // 한국어 이름
  public final String koreanName;

  // 생성
  public static Region of(String name) {
    return Arrays.stream(values())
            .filter(region -> name.equals(region.name()))
            .findFirst()
            .orElseThrow(() -> {
              throw new NotFoundException(ErrorMessage.REGION_NOT_FOUND);
            });
  }
}
