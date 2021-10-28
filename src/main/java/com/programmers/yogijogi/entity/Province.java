package com.programmers.yogijogi.entity;

import static com.programmers.yogijogi.entity.Region.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public enum Province {
  Seoul1("서울", List.of(
      Gangnam, Songpa, Seocho
  )),

  Seoul2("서울", List.of(
      MyeongDong, DongDaeMoon, JongRo
  )),

  Seoul3("서울", List.of(
      Mapo, Yeongdeungpo, Yongsan
  )),

  Busan("부산", List.of(
      Haeundae, Yeongdeungpo, Seomyeon
  )),

  Gangwon("강원", List.of(
      Gangneung, Sokcho, Yangyang
  )),

  Jeju("제주", List.of(
      Seogwipo, Region.Jeju
  ));

  // 한국어 이름
  public final String koreanName;

  // 하위 지역
  public final List<Region> regions;
}
