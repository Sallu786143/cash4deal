package com.example.controller;

import com.example.dto.AuthRequest;
import com.example.dto.LoginRequest;
import com.example.entity.ErrorResponse;
import com.example.entity.TokenResponse;
import com.example.exception.BadCredentialsException;
import com.example.service.AuthService;
import com.example.utility.MailService;
import com.example.utility.ValidationUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private MailService mailService;


//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody AuthRequest request ) {
//
//        System.out.println("In register mpodule=====>");
//
//        Optional<String> contactError = ValidationUtil.validateContact(request.getContact());
//        if (contactError.isPresent()) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(contactError.get()));
//        }
//     try {
//            String token = authService.registerUser(
//                    request.getName(),
//                    request.getContact(),
//                    request.getPassword());
//            return ResponseEntity.ok(token);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
//        }
//    }

    @PostMapping("/registered")
    @ResponseBody
    public ResponseEntity<Map<String, String>> processRegister(@RequestBody AuthRequest request) {
        Map<String, String> response = new HashMap<>();

        Optional<String> contactError = ValidationUtil.validateContact(request.getContact());
        if (contactError.isPresent()) {
            response.put("message", contactError.get());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            authService.registerUser(request.getName(), request.getContact(), request.getPassword());

            // Send email asynchronously — no need to block
            mailService.sendRegistrationMail(request.getContact(), request.getName());

            response.put("message", "Registration successful. Confirmation email will be sent shortly.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (MessagingException e) {
            System.err.println("Email sending failed: " + e.getMessage());
            response.put("message", "Registration successful, but failed to send confirmation email.");
            return ResponseEntity.ok(response); // ✅ still 200 OK
        }

    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        System.out.println("Show Registration Form");
        if (!model.containsAttribute("authRequest")) {
            System.out.println("In If=======> Adding authRequest");
            model.addAttribute("authRequest", new AuthRequest());
        } else {
            System.out.println("authRequest already present");
        }
        return "register";
    }


    @GetMapping("/login")
    public String showLoginPage(){
        return "login";
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
//        try {
//            String token = authService.authenticateUser(request.getContact(), request.getPassword());
//            return ResponseEntity.ok(new TokenResponse(token)); // or just return the token
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(new ErrorResponse("Invalid credentials"));
//        }
//        catch (RuntimeException e ) {
//            return ResponseEntity.badRequest()
//                    .body(new ErrorResponse(e.getMessage()));
//        }
//    }


    @GetMapping(value = "/")
    public String helloWorld(){
        return "index";
    }
}
