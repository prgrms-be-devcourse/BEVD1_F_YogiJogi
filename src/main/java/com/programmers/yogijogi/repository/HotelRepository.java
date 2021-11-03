package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT hotel from Hotel hotel join fetch hotel.rooms where hotel.id = :id")
    Optional<Hotel> getByIdWithRooms(Long id);
}
