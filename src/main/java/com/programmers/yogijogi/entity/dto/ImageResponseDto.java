package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponseDto {

    private Long id;
    private String url;
}
