package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.dto.AuthResponse;
import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.TouristService;

@RestController
@RequestMapping("touristapi")
@CrossOrigin("*")
public class TouristController 
{
    @Autowired
    private TouristService service;

    @Autowired
    private JwtUtil jwtUtil;

    // ===================== REGISTER =====================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody Tourist t)
    {
        String msg = service.register(t);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req)
    {
        Tourist user = service.login(req.getEmail(), req.getPassword());

        if (user == null)
        {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid Credentials", "FAIL"));
        }

        String token = jwtUtil.generateToken(user.getEmail(), "TOURIST");

        return ResponseEntity.ok(
            new AuthResponse(token, "TOURIST", user)
        );
    }

    // ===================== UPDATE PROFILE =====================
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody Tourist t)
    {
        String msg = service.updateProfile(t);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== HOMESTAYS =====================
    @GetMapping("/homestays")
    public ResponseEntity<List<Homestay>> homestays()
    {
        return ResponseEntity.ok(service.viewHomestays());
    }

    // ✅ FILTER BY LOCATION
    @GetMapping("/homestays/search/{location}")
    public ResponseEntity<List<Homestay>> searchHomestays(@PathVariable String location)
    {
        return ResponseEntity.ok(service.searchHomestays(location));
    }

    // ===================== ATTRACTIONS =====================
    @GetMapping("/attractions")
    public ResponseEntity<List<Attraction>> attractions()
    {
        return ResponseEntity.ok(service.viewAttractions());
    }

    // ✅ FILTER BY LOCATION
    @GetMapping("/attractions/search/{location}")
    public ResponseEntity<List<Attraction>> searchAttractions(@PathVariable String location)
    {
        return ResponseEntity.ok(service.searchAttractions(location));
    }
    
 // ✅ GET TOURIST BY ID (VERY IMPORTANT)
    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable int id)
    {
        Tourist t = service.getTouristById(id);

        if (t == null)
        {
            throw new RuntimeException("Tourist Not Found");
        }

        return ResponseEntity.ok(t);
    }
    // ===================== GUIDES =====================
    @GetMapping("/guides")
    public ResponseEntity<List<Guide>> guides()
    {
        return ResponseEntity.ok(service.viewGuides());
    }
    
}