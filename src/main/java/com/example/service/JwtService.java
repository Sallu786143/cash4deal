package com.example.service;
import com.example.exception.JwtTokenExpiredException;
import com.example.utility.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


    @Service
    public class JwtService {

        private final JwtProperties jwtProperties;
        private Key secretKey;

        public JwtService(JwtProperties jwtProperties) {
            this.jwtProperties = jwtProperties;
        }

        @PostConstruct
        public void init() {
            this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        }


        public String generateToken(String subject) {
            return Jwts.builder()
                    .setSubject(subject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();
        }

        public String extractUsername(String token) {
            try {
                return Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();
            } catch (ExpiredJwtException e) {

                throw e;
            } catch (SignatureException e) {
                System.out.println("Invalid signature: " + e.getMessage());
                throw e;
            } catch (MalformedJwtException e) {
                System.out.println("Malformed token: " + e.getMessage());
                throw e;
            } catch (IllegalArgumentException e) {
                System.out.println("Empty or null token: " + e.getMessage());
                throw e;
            } catch (Exception e) {
                System.out.println("Unexpected error while parsing token: " + e.getMessage());
                throw e;
            }
        }



        public boolean isTokenValid(String token, UserDetails userDetails) {
            try {
                final String username = extractUsername(token);
                return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
            }
            catch (ExpiredJwtException e){
               throw e;

            }
        }
        private boolean isTokenExpired(String token) {
            Date expirationDate = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expirationDate.before(new Date());
        }
    }


