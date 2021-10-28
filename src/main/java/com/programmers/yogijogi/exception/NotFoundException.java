package com.programmers.yogijogi.exception;

import com.programmers.yogijogi.exception.errors.ErrorMessage;

public class NotFoundException extends RuntimeException{

    private ErrorMessage errorMessage;

    public NotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
