package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Room;
import com.programmers.yogijogi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class ReservationRequestDto {
    private Long id;
    private String name;
}