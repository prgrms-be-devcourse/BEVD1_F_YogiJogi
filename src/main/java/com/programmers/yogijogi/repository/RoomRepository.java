package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.Hotel;
import com.programmers.yogijogi.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    //get으로 가져오면 프록시로 감싼 객체를
    //find 는 optional로 감싸져서 나옴
    Optional<List<Room>> findAllByHotel(Hotel hotel);

    @Query("SELECT r FROM Room AS r WHERE r.stock = 1")
    List<Room> getBYStock();


    @Query("SELECT DISTINCT room "
            + "FROM Room room LEFT JOIN FETCH room.reservations "
            + "JOIN FETCH room.hotel WHERE room.maxGuest <= :guestCnt")
        //guestCnt 이하의 최대 투숙 인원을 가진 방의 정보를 모두 가져온다.
    List<Room> findReservableRoomHasMaxGuestCntUnder(int guestCnt);
}
