package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.entity.dto.RegionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionController {

  @RequestMapping("/")
  public ResponseEntity<RegionResponseDto> getRegions() {
    return ResponseEntity.ok(
        new RegionResponseDto()
    );
  }
}
