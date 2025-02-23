package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.exceptions.HotelCreationException;
import com.gpsolutions.hoteltask.exceptions.HotelNotFoundException;
import com.gpsolutions.hoteltask.exceptions.InvalidFilterParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError)error).getField();
                    String errorMessage  = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        problemDetail.setProperty("errors", errors);
        return ResponseEntity
                .badRequest()
                .body(problemDetail);
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<String> handleHotelNotFound(HotelNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(HotelCreationException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(HotelCreationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("message", ex.getMessage());
        problemDetail.setProperty("exception", ex.getCause().getMessage());
        return ResponseEntity
                .badRequest()
                .body(problemDetail);
    }

    @ExceptionHandler(InvalidFilterParameterException.class)
    public ResponseEntity<String> handleIllegalParameter(InvalidFilterParameterException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
