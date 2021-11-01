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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    @Autowired
    private final RoomRepository roomRepository;
    @Autowired
    private final HotelRepository hotelRepository;
    @Autowired
    private final ReservationRepository reservationRepository;
    @Autowired
    private final RoomConverter roomConverter;

    public RoomDto findOne(Long id) throws NotFoundException {
        return roomRepository.findById(id)
                .map(roomConverter::convertRoomDto)
                .orElseThrow(() -> new NotFoundException("룸을 찾을 수 없습니다."));
    }

    public List<RoomDto> findAllByHotelId(Long hotelId) {
        Hotel findHotel = hotelRepository.getById(hotelId);
        List<Room> rooms = roomRepository.findAllByHotel(findHotel).orElseThrow();
        return rooms.stream().map(roomConverter::convertRoomDto).collect(toList());
    }


    @Transactional
    public List<RoomDto> findAllByDate2(Long hotelId, LocalDate checkIn, LocalDate checkOut) throws NotFoundException {
        Hotel findHotel = hotelRepository.getById(hotelId);
        List<Room> rooms = roomRepository.findAllByHotel(findHotel).orElseThrow(() -> new NotFoundException("룸을 찾을 수 없습니다."));


        for (Room room : rooms) {
            List<Reservation> reservations = reservationRepository.getAllByRoom(room);
            for (Reservation reservation : reservations) {
                if ((checkIn.isBefore(reservation.getCheckOut()) && checkIn.isAfter(reservation.getCheckIn())) ||
                        (checkOut.isBefore(reservation.getCheckOut()) && checkOut.isAfter(reservation.getCheckIn()))) {
                    reservation.getRoom().minusRoomStock();
                }
            }
        }
        List<Room> result = roomRepository.getBYStock();
        if (result.isEmpty()) {
            throw new NotEnoughStockException("사용 가능한 룸이 없습니다.");
        }
        return result.stream().map(roomConverter::convertRoomDto).collect(toList());

//        List<Room> result = roomRepository.findBYStock().orElseThrow(() -> new NotFoundException("예약 가능한 객실이 없습니다."));
//        return result.stream().map(roomConverter::convertRoomDto).collect(toList());
    }

}
