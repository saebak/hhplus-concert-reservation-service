package com.hhplus.backend.domain.exception;

public class NotFoundException extends NullPointerException {

    NotFoundException() {}

    public NotFoundException(String message) {
        super(message);
    }
}