package com.gpsolutions.hoteltask.api.controllers;

import com.gpsolutions.hoteltask.exceptions.HotelCreationException;
import com.gpsolutions.hoteltask.exceptions.HotelNotFoundException;
import com.gpsolutions.hoteltask.exceptions.InvalidFilterParameterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail  handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("IllegalArgumentException");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleIllegalArgument(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("MethodArgumentNotValidException");
        Map<String,String> errors = createErrorMap(ex);
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public ProblemDetail handleHotelNotFound(HotelNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("HotelNotFoundException");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(HotelCreationException.class)
    public ProblemDetail handleIllegalArgument(HotelCreationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("HotelCreationException");
        problemDetail.setDetail(ex.getCause().getMessage());
        return problemDetail;
    }

    @ExceptionHandler(InvalidFilterParameterException.class)
    public ProblemDetail handleIllegalParameter(InvalidFilterParameterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("InvalidFilterParameterException");
            problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    private Map<String,String> createErrorMap(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError)error).getField(),
                        error -> error.getDefaultMessage()
                ));
    }
}
