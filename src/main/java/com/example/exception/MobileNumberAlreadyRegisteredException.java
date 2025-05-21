package com.example.exception;

public class MobileNumberAlreadyRegisteredException extends RuntimeException {
    public MobileNumberAlreadyRegisteredException(String message) {
        super(message);
    }

}
