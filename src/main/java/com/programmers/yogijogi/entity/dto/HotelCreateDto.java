package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Region;
import com.programmers.yogijogi.entity.Theme;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HotelCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String region;

    @NotNull
    @PositiveOrZero
    private int grade;

    @NotBlank
    private String theme;

    @Builder
    public HotelCreateDto(String name, String region, int grade, String theme) {
        this.name = name;
        this.region = region;
        this.grade = grade;
        this.theme = theme;
    }

    public Hotel toEntity() {
        return Hotel.builder()
                .name(this.getName())
                .region(Region.of(this.getRegion()))
                .theme(Theme.of(this.getTheme()))
                .grade(this.getGrade())
                .build();
    }
}
