package com.programmers.yogijogi.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class ImageTest {

    @Test
    void addHotel() {
        // given
        Hotel hotel = Hotel.builder().build();

        Image image = Image.builder()
                .url("www.naver.com")
                .build();

        // when
        hotel.addImage(image);

        // then
        assertThat(hotel.getImages(), contains(image));
        assertThat(image.getHotel(), is(hotel));
    }
}