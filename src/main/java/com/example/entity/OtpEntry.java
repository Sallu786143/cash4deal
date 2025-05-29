package com.example.entity;


import java.time.LocalDateTime;

public class OtpEntry {
    private final String code;
    private final LocalDateTime createdAt;

    public OtpEntry(String code) {
        this.code = code;
        this.createdAt = LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
