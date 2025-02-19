package com.gpsolutions.hoteltask.exceptions;

public class MethodNotCreatedException extends RuntimeException {

    public MethodNotCreatedException(String message) {
        super(message);
    }


    public MethodNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNotCreatedException(Throwable cause) {
        super(cause);
    }
}