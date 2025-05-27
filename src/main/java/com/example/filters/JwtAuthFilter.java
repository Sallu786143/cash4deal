package com.example.filters;

import com.example.exception.JwtTokenExpiredException;
import com.example.service.CustomUserDetailsService;
import com.example.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtutil;
    private CustomUserDetailsService serviceDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        try {

            String uri = request.getRequestURI();
            System.out.println("JWT Filter processing: " + uri);

            // Define an array of regex patterns for static resources
            String[] staticPaths = {
                    "^/css/.*", "^/js/.*", "^/img/.*", "^/fonts/.*", "^/images/.*",
                    "^/.*", "^/bootstrap/.*", "^/fancybox/.*", "^/fonts.spartan/.*",
                    "^/jquery/.*", "^/jquery.cookie/.*", "^/jquery.elevateZoom/.*",
                    "^/masonry/.*", "^/sass/.*", "^/slick-carouse/.*","^/register/.*","^/registered/.*"
            };

            for (String pattern : staticPaths) {
                if (uri.matches(pattern)) {
                    System.out.println("Bypassing security for static resource: " + uri);
                    filterChain.doFilter(request, response);
                    return;
                }
            }





            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Missing or invalid Authorization header\"}");
                return;
            }
            if (authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtutil.extractUsername(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = serviceDetails.loadUserByUsername(username);
                System.out.println("Try block");
                if (jwtutil.isTokenValid(token, userDetails)) {
                    // If token is valid, set authentication in the SecurityContext
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (ExpiredJwtException | JwtTokenExpiredException e) {
            // 🔴 Handle the expired token error directly
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"JWT token has expired. Please login again.\"}");
            return;
        } catch (Exception e) {
            // Optional: handle other exceptions
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"An internal error occurred.\"}");
            return;
        }
        filterChain.doFilter(request, response);  // Continue with the filter chain
    }

    }
