package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String email;
    private String mobile;

    private String password;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
