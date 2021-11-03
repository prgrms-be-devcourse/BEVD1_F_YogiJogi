package com.programmers.yogijogi.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Slf4j
class HotelRepositoryTest {

  @Autowired
  HotelRepository hotelRepository;

  @ArgumentsSource(HotelInfoProvider.class)
  @ParameterizedTest
  @Transactional
  public void findHotelWithRooms(Hotel hotel) {
      hotel = hotelRepository.save(hotel);
      hotelRepository.flush();

      Hotel hotelWithRooms = hotelRepository.getByIdWithRooms(hotel.getId())
              .orElseThrow(() -> new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND));

      assertThat(hotelWithRooms.getRooms().size(), is(3));
  }

  @ArgumentsSource(HotelInfoProvider.class)
  @ParameterizedTest
  @Transactional
  public void findHotelsWithDateAndRegionAndGuestCnt(Hotel hotel, int queryResultCnt) {
//    Hotel hotel = Hotel.builder().build()
//        .addRooms(
//            List.of(
//                Room.builder().maxGuest(TEST_GUEST_CNT).build()
//                    .addReservations(List.of(
//                        Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
//                    )),
//                Room.builder().maxGuest(TEST_GUEST_CNT).build()
//                    .addReservations(List.of(
//                        Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
//                    )),
//                Room.builder().maxGuest(TEST_GUEST_CNT).build()
//                    .addReservations(List.of(
//                        Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
//                    ))
//            )
//        );

    //given
    hotelRepository.save(hotel);
    hotelRepository.flush();

    log.info("rooms count : " + hotel.getRooms().size());
    for(Room room : hotel.getRooms()){
      log.info("reservations : " + room.getReservations().size());
    }

    //when
//    List<Hotel> hotels = new ArrayList<>(hotelRepository.findHotelsByCheckInAndCheckOut());
//    assertThat(hotels.size(), is(queryResultCnt));

//    hotel = hotels.get(0);
//    for (Room room : hotel.getRooms()) {
//      assertThat(room.getReservations().size(), is(1));
//    }
  }

  static final LocalDate TEST_BASE_DATE = LocalDate.of(2020, 1, 1);
  static final int TEST_GUEST_CNT = 3;

  static class HotelInfoProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext)
        throws Exception {
      return Stream.of(
          Arguments.of(
              Hotel.builder().build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              )),
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              )),
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              ))
                      )
                  ),
              1
          ), //  Hotel에 속한 방들이 모두 TEST_BASE_DATE의 +1일 이내에 예약이 되어 있을 경우

          Arguments.of(
              Hotel.builder().build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build()
                      )
                  ),
              1 //  Hotel에 속한 방들이 모두 예약은 안되어 있지만, 방들의 최대 수용 인원이 TEST_GUEST_CNT보다 큰 경우
          ),

          Arguments.of(
              Hotel.builder().build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT-1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT-1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT-1).build()
                      )
                  ),
              1 //  Hotel에 속한 방들이 모두 예약이 안되어 있고, 방들의 최대 수용 인원도 TEST_GUEST_CNT보다 작은 경우
          ),

          Arguments.of(
              Hotel.builder().build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              )),
                          // 인원은 수용 가능하지만 해당 날짜에 예약이 잡혀있는 경우

                          Room.builder().maxGuest(TEST_GUEST_CNT).build(),
                          // 인원도 수용이 가능하고 해당 날짜에 예약이 잡혀있지 않은 경우,

                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE).checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              ))
                          // 인원도 수용이 불가능하고 예약도 이미 잡혀있는 경우,
                      )
                  ),
              1
          )
      );
    }
  }


}