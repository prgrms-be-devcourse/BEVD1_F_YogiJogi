package com.programmers.yogijogi.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class HostTest {

    @Test
    @DisplayName("host_hotel 연관관계 편의 메소드 확인")
    void addHotel() {
        Host host = Host.builder().name("testName").build();

        Hotel hotel = Hotel.builder().name("testHotel1").build();
        Hotel hotel2 = Hotel.builder().name("testHotel2").build();

        host.addHotel(hotel);
        host.addHotel(hotel2);

        assertThat(host.getHotels(), contains(hotel, hotel2));
        assertThat(hotel.getHost(), is(host));
        assertThat(hotel2.getHost(), is(host));
    }
}