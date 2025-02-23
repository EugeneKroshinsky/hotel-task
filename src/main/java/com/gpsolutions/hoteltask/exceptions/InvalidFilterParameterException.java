package com.gpsolutions.hoteltask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such parameters")
public class InvalidFilterParameterException extends RuntimeException {
    public InvalidFilterParameterException(String message) {
        super(message);
    }
}
