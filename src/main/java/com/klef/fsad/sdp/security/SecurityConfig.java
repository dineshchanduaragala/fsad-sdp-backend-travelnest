package com.klef.fsad.sdp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.*;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    // ✅ Disable default user
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> null;
    }

    // ===================== ✅ FIXED CORS =====================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🔥 IMPORTANT FIX
        config.setAllowedOriginPatterns(List.of("*"));   // allow all origins
        config.setAllowedMethods(List.of("*"));          // allow all methods
        config.setAllowedHeaders(List.of("*"));          // allow all headers

        config.setAllowCredentials(false); // 🔥 VERY IMPORTANT (for images)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    // ===================== SECURITY =====================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> {}) // ✅ enable CORS
            .csrf(csrf -> csrf.disable())

            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            .sessionManagement(sess ->
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(

                    // ✅ PUBLIC AUTH
                    "/auth/**",
                    "/adminapi/login",
                    "/adminapi/dashboard",
                    "/hostapi/login",
                    "/touristapi/login",
                    "/guideapi/login",
                    "/**/register",

                    // 🔥🔥🔥 CRITICAL FIX FOR IMAGES
                    "/homestayapi/image/**",
                    "/homestayapi/qr/**",
                    "/attractionapi/image/**",

                    // OTHER
                    "/uploads/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                .anyRequest().authenticated()
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}