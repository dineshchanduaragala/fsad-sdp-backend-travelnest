package com.klef.fsad.sdp.security;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil 
{
    private final String SECRET = "mysecretkeymysecretkeymysecretkey12345"; // ✅ 32+ chars

    private Key getKey()
    {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ===================== GENERATE =====================
    public String generateToken(String username, String role)
    {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hrs
                .signWith(getKey()) // ✅ updated (no algo needed)
                .compact();
    }

    // ===================== EXTRACT =====================
    public String extractUsername(String token)
    {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token)
    {
        return (String) getClaims(token).get("role");
    }

    // ===================== VALIDATE =====================
    public boolean validateToken(String token)
    {
        try 
        {
            Claims claims = getClaims(token);

            // ✅ Expiry check
            return !claims.getExpiration().before(new Date());
        } 
        catch (Exception e) 
        {
            return false;
        }
    }

    // ===================== INTERNAL =====================
    private Claims getClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}