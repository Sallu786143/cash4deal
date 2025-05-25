package com.example.config;


import com.example.filters.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Allow static resources to be accessed without authentication
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/bootstrap/**",
                                "/fonts/**",
                                "/images/**",  // This already covers all images under /images/
                                "/images/card-product/**",  // You could add this explicitly if images are nested
                                "/fancybox/**",
                                "/jquery/**",
                                "/jquery.cookie/**",
                                "/jquery.elevateZoom/**",
                                "/masonry/**",
                                "/sass/**",
                                "/slick-carouse/**",
                                "/api/index/**",
                                "/images/**",  // This should cover all images under /images/
                                "/images/card-product/**",  // Ensure images in nested directories are accessible
                                "/api/images/**"// Add this to explicitly allow /api/images/ to be accessible
                        ).
                        permitAll()

                        // Ensure other requests are authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) // Form login if needed, or you can configure JWT login
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions, JWT only
                )
                .formLogin(AbstractHttpConfigurer::disable) // Disable form login page
                .httpBasic(AbstractHttpConfigurer::disable) // Disable basic authentication

                // Add your custom JWT filter, but exclude static resources from it
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/css/**", "/js/**", "/images/**")); // Disable CSRF for static resources if needed

        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



