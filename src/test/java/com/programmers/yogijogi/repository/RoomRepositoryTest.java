package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@Slf4j
class RoomRepositoryTest {

  private static final String TEST_HOTEL_NAME = "some hotel name";

  @Autowired
  HotelRepository hotelRepository;

  @Autowired
  RoomRepository roomRepository;

  @Test
  @Transactional
  void findRoom() {
    //given
    Hotel hotel = Hotel.builder().name(TEST_HOTEL_NAME).build();
    LocalDate date = LocalDate.now();
    Room room = Room.builder().maxGuest(1).build()
        .addReservations(
            List.of(
                Reservation.builder().checkIn(date).checkOut(date.plusDays(1)).build(),
                Reservation.builder().checkIn(date).checkOut(date.plusDays(1)).build(),
                Reservation.builder().checkIn(date).checkOut(date.plusDays(1)).build(),
                Reservation.builder().checkIn(date).checkOut(date.plusDays(1)).build(),
                Reservation.builder().checkIn(date).checkOut(date.plusDays(1)).build()
            )
        );
    hotel.addRoom(room);

    //when
    hotelRepository.save(hotel);
    hotelRepository.flush();

    //then
    List<Room> rooms1 = roomRepository.findReservableRoomHasMaxGuestCntUnder(1);
    List<Room> rooms2 = roomRepository.findReservableRoomHasMaxGuestCntUnder(0);
    assertThat(rooms1.size(), is(1));
    assertThat(rooms2.size(), is(0));
    assertThat(rooms1.get(0).getHotel().getName(), equalTo(TEST_HOTEL_NAME));
    assertThat(rooms1.get(0).getReservations().size(), is(5));
  }
}