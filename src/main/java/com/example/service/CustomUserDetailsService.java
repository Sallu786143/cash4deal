package com.example.service;

//import com.example.entity.User;
//import com.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

//public class CustomUserDetailsService implements UserDetailsService {
public class CustomUserDetailsService {

//    @Autowired
//    private UserRepository userRepository;
  //  @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByName(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
//
//        return new org.springframework.security.core.userdetails.User(
//                user.getName(),                        // Username
//                user.getPassword(),                    // Hashed password
//                Collections.singletonList(
//                        new SimpleGrantedAuthority("ROLE_USER") // Default role
//                )
//        );
//    }
}
