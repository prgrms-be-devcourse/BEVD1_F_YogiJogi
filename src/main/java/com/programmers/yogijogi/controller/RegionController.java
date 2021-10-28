package com.programmers.yogijogi.controller;

import com.programmers.yogijogi.common.ApiResponse;
import com.programmers.yogijogi.entity.dto.RegionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegionController {

  @RequestMapping("api/v1/hotels")
  public ResponseEntity<ApiResponse<RegionResponseDto>> getRegions() {
    return ResponseEntity.ok(
        ApiResponse.ok(
            new RegionResponseDto()
        )
    );
  }
}
