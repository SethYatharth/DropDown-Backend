package com.dropdown.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(ServiceProviderException.class)
    public ResponseEntity<ExceptionMessage> serviceProviderExceptionHandler(ServiceProviderException e, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionMessage> userExceptionHandler(UserException e, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionMessage> usernameNotFoundExceptionHandler(UsernameNotFoundException e, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RideException.class)
    public ResponseEntity<ExceptionMessage> handleRideException(RideException e, WebRequest request) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(e.getMessage(),request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

}
