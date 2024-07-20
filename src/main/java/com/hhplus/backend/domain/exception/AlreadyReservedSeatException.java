package com.hhplus.backend.domain.exception;

import org.apache.coyote.BadRequestException;

public class AlreadyReservedSeatException extends Exception {

    AlreadyReservedSeatException() {}

    public AlreadyReservedSeatException(String message) {
        super(message);
    }
}