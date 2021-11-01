package com.programmers.yogijogi.repository;

import com.programmers.yogijogi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
