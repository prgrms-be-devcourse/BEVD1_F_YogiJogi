package com.programmers.yogijogi.entity;

import com.programmers.yogijogi.exception.NotFoundException;
import com.programmers.yogijogi.exception.errors.ErrorMessage;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Theme {
    PC("pc");

    private final String name;

    Theme(String name) {
        this.name = name;
    }

    // 생성
    public static Theme of(String name) {
        return Arrays.stream(values())
                .filter(theme -> name.equals(theme.getName()))
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.THEME_NOT_FOUND);
                });
    }
}
