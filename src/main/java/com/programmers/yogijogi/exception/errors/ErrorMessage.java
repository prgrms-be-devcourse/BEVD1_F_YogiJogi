package com.programmers.yogijogi.exception.errors;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorMessage {
    //COMMON
    HOTEL_NOT_FOUND("해당 호텔은 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR("예상치 못한 서버 문제 입니다."),
    NOT_FILE_CONVERT("파일을 변환 할 수 없습니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public static ErrorMessage of(String message) {
        return Arrays.stream(values())
                .filter(errorMessage -> errorMessage.message.equals(message))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(ErrorMessage.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
