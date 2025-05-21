package com.example.controller;

import com.example.dto.AuthRequest;
import com.example.dto.LoginRequest;
import com.example.entity.ErrorResponse;
import com.example.entity.TokenResponse;
import com.example.exception.BadCredentialsException;
import com.example.service.AuthService;
import com.example.utility.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request ) {

        Optional<String> contactError = ValidationUtil.validateContact(request.getContact());
        if (contactError.isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorResponse(contactError.get()));
        }
     try {
            String token = authService.registerUser(
                    request.getName(),
                    request.getContact(),
                    request.getPassword());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.authenticateUser(request.getContact(), request.getPassword());
            return ResponseEntity.ok(new TokenResponse(token)); // or just return the token
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
        }
        catch (RuntimeException e ) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }


    @GetMapping(value = "/index")
    public String helloWorld(){
        return "index";
    }
}
