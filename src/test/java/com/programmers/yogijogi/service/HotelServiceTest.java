package com.programmers.yogijogi.service;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Province;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.ReservableHotelRequestDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Slf4j
public class HotelServiceTest {

  private static final String TEST_HOTEL_NAME = "some hotel name";
  static final LocalDate TEST_BASE_DATE = LocalDate.of(2020, 1, 1);
  static final int TEST_GUEST_CNT = 3;

  @Autowired
  HotelService hotelService;

  @Autowired
  HotelRepository hotelRepository;

  @Autowired
  RoomRepository roomRepository;

  @ArgumentsSource(HotelInfoProvider.class)
  @ParameterizedTest
  @Transactional
  public void findHotelsWithDateAndRegionAndGuestCnt(Hotel hotel, int queryResultCnt) {
    //given
    hotelRepository.save(hotel);
    hotelRepository.flush();

    //when
    List<Hotel> reservableRoomsWithCheckInAndCheckOut = hotelService.getReservableHotels(new ReservableHotelRequestDto(
        Province.Seoul1,
        TEST_BASE_DATE,
        TEST_BASE_DATE.plusDays(1),
        TEST_GUEST_CNT
    ));

    //then
    assertThat(reservableRoomsWithCheckInAndCheckOut.size(), is(queryResultCnt));
  }

  static class HotelInfoProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext)
        throws Exception {
      return Stream.of(
          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build(),

                                  Reservation.builder().checkIn(TEST_BASE_DATE.plusDays(2))
                                      .checkOut(TEST_BASE_DATE.plusDays(3)).build()

                              )),
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              )),
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              ))
                      )
                  ),
              0
          ), //  Hotel에 속한 방들이 모두 TEST_BASE_DATE의 +1일 이내에 예약이 되어 있을 경우

          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build()
                      )
                  ),
              0 //  Hotel에 속한 방들이 모두 예약은 안되어 있지만, 방들의 최대 수용 인원이 TEST_GUEST_CNT보다 큰 경우
          ),

          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT - 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT - 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT - 1).build()
                      )
                  ),
              1 //  Hotel에 속한 방들이 모두 예약이 안되어 있고, 방들의 최대 수용 인원도 TEST_GUEST_CNT보다 작은 경우
          ),

          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              )),
                          // 인원은 수용 가능하지만 해당 날짜에 예약이 잡혀있는 경우

                          Room.builder().maxGuest(TEST_GUEST_CNT).build(),
                          // 인원도 수용이 가능하고 해당 날짜에 예약이 잡혀있지 않은 경우,

                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              ))
                          // 인원도 수용이 불가능하고 예약도 이미 잡혀있는 경우,
                      )
                  ),
              1
          )
      );
    }
  }

  @ArgumentsSource(HotelInfoProviderForDateFiltering.class)
  @ParameterizedTest
  @Transactional
  public void findRoomsWithDate(Hotel hotel, int queryResultCnt) {
    //given
    hotelRepository.save(hotel);
    hotelRepository.flush();
    List<Room> reservableRoomsWithCheckInAndCheckOut = hotelService.filterReservableRoomsWithCheckInAndCheckOut(new ArrayList<>(hotel.getRooms()), TEST_BASE_DATE, TEST_BASE_DATE.plusDays(1));
    assertThat(reservableRoomsWithCheckInAndCheckOut.size(), is(queryResultCnt));
  }

  static class HotelInfoProviderForDateFiltering implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext)
        throws Exception {
      return Stream.of(
          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build(),

                                  Reservation.builder().checkIn(TEST_BASE_DATE.plusDays(2))
                                      .checkOut(TEST_BASE_DATE.plusDays(3)).build()

                              )),
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              )),
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              ))
                      )
                  ),
              0
          ), //  Hotel에 속한 방들이 모두 TEST_BASE_DATE의 +1일 이내에 예약이 되어 있을 경우

          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build()
                      )
                  ),
              3 //  Hotel에 속한 방들이 모두 예약은 안되어 있지만, 방들의 최대 수용 인원이 TEST_GUEST_CNT보다 큰 경우
          ),

          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT - 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT - 1).build(),
                          Room.builder().maxGuest(TEST_GUEST_CNT - 1).build()
                      )
                  ),
              3 //  Hotel에 속한 방들이 모두 예약이 안되어 있고, 방들의 최대 수용 인원도 TEST_GUEST_CNT보다 작은 경우
          ),

          Arguments.of(
              Hotel.builder().name(TEST_HOTEL_NAME).build()
                  .addRooms(
                      List.of(
                          Room.builder().maxGuest(TEST_GUEST_CNT).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
                              )),
                          // 인원은 수용 가능하지만 해당 날짜에 예약이 잡혀있는 경우

                          Room.builder().maxGuest(TEST_GUEST_CNT).build(),
                          // 인원도 수용이 가능하고 해당 날짜에 예약이 잡혀있지 않은 경우,

                          Room.builder().maxGuest(TEST_GUEST_CNT + 1).build()
                              .addReservations(List.of(
                                  Reservation.builder().checkIn(TEST_BASE_DATE)
                                      .checkOut(TEST_BASE_DATE.plusDays(1)).build()
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
