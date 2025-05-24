package com.example.exception;

import com.example.entity.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class JwtExceptionHandler {
    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<Map<String, String>> handleJwtTokenExpiredException(JwtTokenExpiredException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage()); // or a user-friendly message
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED); // 401
    }


    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        // Return a custom message for other JWT exceptions
        return new ResponseEntity<>("Invalid JWT token.", HttpStatus.UNAUTHORIZED);
    }
}
