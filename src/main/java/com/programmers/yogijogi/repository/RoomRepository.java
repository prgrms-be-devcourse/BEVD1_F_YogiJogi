package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.Room;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
  @Query("SELECT DISTINCT room "
      + "FROM Room room LEFT JOIN FETCH room.reservations "
      + "JOIN FETCH room.hotel WHERE room.maxGuest <= :guestCnt")
  //guestCnt 이하의 최대 투숙 인원을 가진 방의 정보를 모두 가져온다.
  List<Room> findReservableRoomHasMaxGuestCntUnder(int guestCnt);
}
