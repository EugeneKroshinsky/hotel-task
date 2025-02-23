package com.gpsolutions.hoteltask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Hotel")
public class HotelNotFoundException extends RuntimeException{
    public HotelNotFoundException(Long id) {
        super("Hotel with id " + id + " not found");
    }
}
