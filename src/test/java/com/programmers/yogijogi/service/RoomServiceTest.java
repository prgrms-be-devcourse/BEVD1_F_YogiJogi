package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.RoomConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.RoomDto;
import com.programmers.yogijogi.exception.NotEnoughStockException;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@Rollback(false)
@Slf4j
class RoomServiceTest {
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomConverter roomConverter;


    Long savedHotelId;
    Long savedRoomId1;
    Long savedRoomId2;

    @BeforeEach
    void save_test() {

        Hotel hotel1 = Hotel.builder()
                .name("신라스테이")
                .build();

        Room room1 = Room.builder()
                .name("디럭스룸")
                .price(70000)
                .stock(1)
                .maxGuest(2)
                .hotel(hotel1)
                .build();

        Room room2 = Room.builder()
                .name("룸룸")
                .price(70000)
                .stock(1)
                .maxGuest(2)
                .hotel(hotel1)
                .build();

        savedHotelId = hotelRepository.save(hotel1).getId();
        savedRoomId1 = roomRepository.save(room1).getId();
        savedRoomId2 = roomRepository.save(room2).getId();
        Optional<Room> findroom = roomRepository.findById(savedRoomId1);
        Assertions.assertThat(findroom.get().getName()).isEqualTo("디럭스룸");
        Assertions.assertThat(room1.getHotel().getId()).isEqualTo(room2.getHotel().getId());

    }


    @Test
    void findAllByDate() throws NotFoundException {
        RoomDto findRoom = roomService.findOne(savedRoomId1);
        Reservation reservation1 = Reservation.builder()
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        reservationRepository.save(reservation1);

        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4);
        List<RoomDto> rooms = roomService.findAllByDate2(savedHotelId, checkIn, checkOut);
        Assertions.assertThat(rooms.get(0).getName()).isEqualTo("룸룸");
        Assertions.assertThat(rooms.size()).isEqualTo(1);

    }

    @Test
    @DisplayName("예약가능한 방이 없을 경우 예외를 던져야한다 ")
    void notRooms() throws NotFoundException {
        RoomDto findRoom = roomService.findOne(savedRoomId1);
        RoomDto findRoom2 = roomService.findOne(savedRoomId2);
        Reservation reservation1 = Reservation.builder()
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        Reservation reservation2 = Reservation.builder()
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom2))
                .build();

        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);


        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4);
//        List<RoomDto> rooms = roomService.findAllByDate2(savedHotelId, checkIn, checkOut);
        assertThrows(NotEnoughStockException.class, () -> roomService.findAllByDate2(savedHotelId, checkIn, checkOut));
    }

}
