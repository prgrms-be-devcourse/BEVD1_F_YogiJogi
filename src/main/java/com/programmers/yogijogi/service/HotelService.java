package com.programmers.yogijogi.service;

import com.programmers.yogijogi.converter.HotelConverter;
import com.programmers.yogijogi.converter.ImageConverter;
import com.programmers.yogijogi.converter.ReviewConverter;
import com.programmers.yogijogi.converter.RoomConverter;
import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Image;
import com.programmers.yogijogi.entity.Reservation;
import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.dto.*;
import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import com.programmers.yogijogi.repository.HotelRepository;
import com.programmers.yogijogi.repository.ImageRepository;
import com.programmers.yogijogi.repository.ReviewRepository;
import com.programmers.yogijogi.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    // 하나의 호텔을 조회한다.
    @Transactional
    public HotelDetailResponseDto getOne(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND);
                });

        return HotelConverter.of(hotel);
    }

    // 호텔 image url을 저장한다.
    @Transactional
    public void saveHotelImageUrl(Long id, String url) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND);
                });

        hotel.addImage(new Image(url));
    }

    @Transactional
    public Page<ImageResponseDto> getImageByHotelId(Long hotelId, Pageable pageable) {
        return imageRepository.findImageByHotelId(hotelId, pageable)
                .map(ImageConverter::of);
    }

    @Transactional
    public List<ReservableHotelResponseDto> getReservableHotelsBy(
            ReservableHotelRequestDto reservableHotelRequestDto) {
        List<Hotel> reservableHotels = getReservableHotels(reservableHotelRequestDto);

        List<ReservableHotelResponseDto> dto = reservableHotels.stream()
                .map(HotelConverter::convertToReservableHotelResponseDto)
                .collect(Collectors.toList());

        return dto;
    }

    // 호텔을 저장한다.
    @Transactional
    public Long save(HotelCreateDto hotelCreateDto) {
        Hotel hotelEntity = hotelRepository.save(hotelCreateDto.toEntity());
        return hotelEntity.getId();
    }

    // 2개의 리뷰를 가져온다.
    @Transactional
    public List<ReviewResponseDto> getTwoReviewsByHotelId(Long id) {
        return reviewRepository.findTop2ByHotelId(id)
                .stream().map(review -> ReviewConverter.of(review))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Hotel> getReservableHotels(
            ReservableHotelRequestDto reservableHotelRequestDto) {
        // 예약 최대 인원으로 필터링
        List<Room> rooms = this.roomRepository.findReservableRoomHasMaxGuestCntUnder(
                reservableHotelRequestDto.getGuestCnt());

        // checkIn checkOut 날짜를 이용해서 필터링
        List<Room> reservableRooms = this.filterReservableRoomsWithCheckInAndCheckOut(
                rooms,
                reservableHotelRequestDto.getCheckIn(),
                reservableHotelRequestDto.getCheckOut()
        );

        return reservableRooms.stream()
                .map(Room::getHotel)
                .distinct()
                .collect(Collectors.toList());

    }

    @Transactional
    public List<Room> filterReservableRoomsWithCheckInAndCheckOut(List<Room> rooms, LocalDate checkIn,
                                                                  LocalDate checkOut) {

        Predicate<Reservation> reservationPredicateCheckIn = reservation ->
                (reservation.getCheckIn().isEqual(checkIn)
                        || reservation.getCheckIn().isAfter(checkIn))
                        && (reservation.getCheckIn().isEqual(checkOut)
                        || reservation.getCheckIn().isBefore(checkOut));

        Predicate<Reservation> reservationPredicateCheckOut = reservation ->
                (reservation.getCheckOut().isEqual(checkIn)
                        || reservation.getCheckOut().isAfter(checkIn))
                        && (reservation.getCheckOut().isEqual(checkOut)
                        || reservation.getCheckOut().isBefore(checkOut));

        return rooms.stream().filter(
                room -> {
                    long reservationsCnt = room.getReservations().stream()
                            .filter(reservationPredicateCheckIn.or(reservationPredicateCheckOut))
                            .count();

                    log.info("reservationsCnt -> {}", reservationsCnt);

                    return reservationsCnt == 0;
                }
        ).collect(Collectors.toList());
    }

    @Transactional
    public List<ReservableRoomResponseDto> getReservableRooms(Long id, LocalDate checkIn, LocalDate checkOut) {
        Hotel hotel = hotelRepository.getByIdWithRooms(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.HOTEL_NOT_FOUND));

        return this.filterReservableRoomsWithCheckInAndCheckOut(
                        hotel.getRooms(),
                        checkIn,
                        checkOut
                )
                .stream().map(r -> RoomConverter.of(r)).collect(Collectors.toList());
    }
}
