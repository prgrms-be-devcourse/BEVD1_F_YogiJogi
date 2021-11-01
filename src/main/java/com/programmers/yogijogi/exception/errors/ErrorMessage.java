package com.programmers.yogijogi.exception.errors;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorMessage {
    //COMMON
    HOTEL_NOT_FOUND("해당 호텔은 존재하지 않습니다."),
    REGION_NOT_FOUND("해당 지역은 존재하지 않습니다."),
    THEME_NOT_FOUND("해당 테마는 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR("예상치 못한 서버 문제 입니다."),
    NOT_FILE_CONVERT("파일을 변환 할 수 없습니다."),
    ROOM_NOT_FOUND("해당 객실은 존재하지 않습니다."),
    NOT_USABLE_ROOM("예약 가능한 객실이 없습니다. 날짜를 변경해주세요 ");



    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
