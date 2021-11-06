package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.RoomConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.User;
import com.programmers.yogijogi.entity.dto.RoomDetailResponseDto;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ReservationRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import com.programmers.yogijogi.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("test")
class RoomServiceTest {
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserRepository userRepository;
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

    static final LocalDate TEST_BASE_DATE = LocalDate.now();
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
    @DisplayName("예약가능한 방이 없을 경우 예외를 던져야한다 ")
    void notRooms() throws NotFoundException {
        RoomDetailResponseDto findRoom = roomService.findOne(savedRoomId1);
        RoomDetailResponseDto findRoom2 = roomService.findOne(savedRoomId2);
        User user = User.builder()
                .name("testUserName")
                .build();
        Reservation reservation1 = Reservation.builder()
                .user(user)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        Reservation reservation2 = Reservation.builder()
                .user(user)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom2))
                .build();

        userRepository.save(user);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);


        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4);
//        List<RoomDto> rooms = roomService.findAllByDate2(savedHotelId, checkIn, checkOut);
        assertThrows(com.programmers.yogijogi.exception.NotFoundException.class, () -> roomService.findAllByDate2(savedHotelId, checkIn, checkOut));
    }

    @Test
    @DisplayName("해당 날짜에 예약이 되어있을 경우 예외를 던져준다. ")
    void findOneByDate() throws NotFoundException {
        RoomDetailResponseDto findRoom = roomService.findOne(savedRoomId1);

        User user = User.builder()
                .name("testUserName")
                .build();

        Reservation reservation1 = Reservation.builder()
                .user(user)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        userRepository.save(user);
        reservationRepository.save(reservation1);

        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4);
        assertThrows(com.programmers.yogijogi.exception.NotFoundException.class, () -> roomService.findOneByDate(savedRoomId1, checkIn, checkOut));

    }

    @Test
    @DisplayName("해당 날짜에 예약이 가능할 경우 객실 상세 조회를 해준다 ")
    void findOneByDate2() throws NotFoundException {
        RoomDetailResponseDto findRoom = roomService.findOne(savedRoomId1);

        User user = User.builder()
                .name("testUserName")
                .build();

        Reservation reservation1 = Reservation.builder()
                .user(user)
                .checkIn(LocalDate.now().plusDays(10))
                .checkOut(LocalDate.now().plusDays(13))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        userRepository.save(user);
        reservationRepository.save(reservation1);

        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4);
        RoomDetailResponseDto room = roomService.findOneByDate(savedRoomId1, checkIn, checkOut);
        Assertions.assertThat(room.getId()).isEqualTo(savedRoomId1);


    }

    @Test
    @DisplayName("날짜를 통해 이용가능한 룸들 조회 ")
    void findAllByDate2() throws NotFoundException {
        RoomDetailResponseDto findRoom = roomService.findOne(savedRoomId1);
        User user = User.builder()
                .name("testUserName")
                .build();

        Reservation reservation1 = Reservation.builder()
                .user(user)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(3))
                .room(roomConverter.convertRoom(findRoom))
                .build();

        userRepository.save(user);
        reservationRepository.save(reservation1);

        LocalDate checkIn = LocalDate.now().plusDays(1);
        LocalDate checkOut = LocalDate.now().plusDays(4);
        List<RoomDetailResponseDto> rooms = roomService.findAllByDate2(savedHotelId, checkIn, checkOut);
        Assertions.assertThat(rooms.get(0).getName()).isEqualTo("룸룸");
        Assertions.assertThat(rooms.size()).isEqualTo(1);
    }
}
