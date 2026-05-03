package com.klef.fsad.sdp.security;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // ✅ 1. Allow CORS preflight requests
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        // ✅ 2. Skip JWT for PUBLIC endpoints
        if (path.equals("/") ||
            path.startsWith("/error") ||

            // AUTH
            path.startsWith("/auth") ||
            path.contains("/login") ||
            path.contains("/register") ||

            // SWAGGER
            path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs") ||

            // PUBLIC APIs (optional)
            path.startsWith("/homestayapi") ||
            path.startsWith("/attractionapi")
        ) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ 3. Extract JWT token
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                if (jwtUtil.validateToken(token)) {

                    String username = jwtUtil.extractUsername(token);

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    Collections.emptyList()
                            );

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }

            } catch (Exception e) {
                // ❌ Invalid token → return 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // ✅ 4. Continue filter chain
        chain.doFilter(request, response);
    }
}