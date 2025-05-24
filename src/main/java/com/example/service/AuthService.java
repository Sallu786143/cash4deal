package com.example.service;

//import com.example.entity.User;
import com.example.exception.BadCredentialsException;
import com.example.exception.EmailAlreadyRegisteredException;
import com.example.exception.MobileNumberAlreadyRegisteredException;
//import com.example.repo.UserRepository;
import com.example.utility.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class AuthService {

//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;




//    public String registerUser(String name, String contact, String password) {
//        if (ValidationUtil.isEmail(contact)) {
//            if (userRepository.findByEmail(contact).isPresent()) {
//                throw new EmailAlreadyRegisteredException("Email already registered");
//            }
//        } else {
//            if (userRepository.findByMobile(contact).isPresent()) {
//                throw new MobileNumberAlreadyRegisteredException("Mobile number already registered");
//            }
//        }
//
//        User user = new User();
//        user.setName(name);
//        user.setPassword(passwordEncoder.encode(password));
//
//        if (ValidationUtil.isEmail(contact)) {
//            user.setEmail(contact);
//        } else {
//            user.setMobile(contact);
//        }
//
//        userRepository.save(user);
//
//        return"User Registerd Successfully";
//
//    //    return jwtUtil.generateToken(user); // Or however you return the token/confirmation
//    }
//
//    public String authenticateUser(String contact, String password) {
//
//        Optional<User> userOpt = userRepository.findByEmail(contact);
//
//        if (userOpt.isEmpty()) {
//            userOpt = userRepository.findByMobile(contact);
//        }
//
//        User user = userOpt.orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Invalid credentials");
//        }
//
//        // ✅ This is where you call jwtService to create the token
//        String token = jwtService.generateToken(user.getEmail());
//
//        return token;
//    }
//



}
