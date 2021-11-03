package com.programmers.yogijogi.entity.dto;

import com.programmers.yogijogi.entity.Hotel;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Long id;
    private String name;
}
