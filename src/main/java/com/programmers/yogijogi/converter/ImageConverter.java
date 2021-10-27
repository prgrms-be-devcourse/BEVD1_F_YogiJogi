package com.programmers.yogijogi.converter;

import com.programmers.yogijogi.entity.Image;
import com.programmers.yogijogi.entity.dto.ImageResponseDto;

public class ImageConverter {

    public static ImageResponseDto of(Image image) {
        return ImageResponseDto.builder()
                .id(image.getId())
                .url(image.getUrl())
                .build();
    }
}
