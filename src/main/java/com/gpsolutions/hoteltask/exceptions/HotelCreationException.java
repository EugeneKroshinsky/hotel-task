package com.gpsolutions.hoteltask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "There are constraints on Hotel table")
public class HotelCreationException extends RuntimeException {
    public HotelCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
