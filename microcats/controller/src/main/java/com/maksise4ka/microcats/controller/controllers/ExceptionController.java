package com.maksise4ka.microcats.controller.controllers;

import com.maksise4ka.microcats.controller.exceptions.SynchronouslyRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(SynchronouslyRequestException.class)
    public ResponseEntity<?> catServiceExceptionThrows() {
        return ResponseEntity.badRequest().build();
    }
}
