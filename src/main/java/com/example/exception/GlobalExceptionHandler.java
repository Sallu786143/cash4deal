package com.example.exception;

import com.example.entity.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<?> handleNotFound(HttpServletRequest request) {
        if (request.getRequestURI().startsWith("/css/") || request.getRequestURI().startsWith("/js/")) {
            return null; // Let Spring handle static resources
        }
        return ResponseEntity.status(404).body(new ErrorResponse("Not Found"));
    }
}