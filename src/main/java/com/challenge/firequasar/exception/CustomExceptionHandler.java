package com.challenge.firequasar.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<String> handleCustomException(ServiceException ex) {
        return new ResponseEntity<>(ex.getErrorMessage(), ex.getHttpStatus());
    }
}