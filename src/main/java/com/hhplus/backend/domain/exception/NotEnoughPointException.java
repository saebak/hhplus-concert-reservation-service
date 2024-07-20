package com.hhplus.backend.domain.exception;

import org.apache.coyote.BadRequestException;

public class NotEnoughPointException extends BadRequestException {

    NotEnoughPointException() {}

    public NotEnoughPointException(String message) {
        super(message);
    }
}