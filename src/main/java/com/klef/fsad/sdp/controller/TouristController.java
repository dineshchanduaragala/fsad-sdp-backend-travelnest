package com.klef.fsad.sdp.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.*;
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

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody Tourist t)
    {
        service.register(t);
        return ResponseEntity.status(201)
                .body(new ApiResponse("Registered Successfully", "SUCCESS"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req)
    {
        Tourist user = service.login(req.getEmail(), req.getPassword());
        String token = jwtUtil.generateToken(user.getEmail(), "TOURIST");

        return ResponseEntity.ok(new AuthResponse(token, "TOURIST", user));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody Tourist t)
    {
        service.updateProfile(t);
        return ResponseEntity.ok(new ApiResponse("Profile Updated", "SUCCESS"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tourist> getTouristById(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getTouristById(id));
    }

    @GetMapping("/homestays")
    public ResponseEntity<List<Homestay>> homestays()
    {
        return ResponseEntity.ok(service.viewHomestays());
    }

    @GetMapping("/homestays/{id}")
    public ResponseEntity<Homestay> getHomestay(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getHomestayById(id));
    }

    @GetMapping("/attractions")
    public ResponseEntity<List<Attraction>> attractions()
    {
        return ResponseEntity.ok(service.viewAttractions());
    }

    @GetMapping("/guides")
    public ResponseEntity<List<Guide>> guides()
    {
        return ResponseEntity.ok(service.viewGuides());
    }

    @GetMapping("/guides/{id}")
    public ResponseEntity<Guide> guideById(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getGuideById(id));
    }

    @PostMapping("/guiderequests")
    public ResponseEntity<ApiResponse> sendGuideRequest(@RequestBody GuideRequest req)
    {
        service.sendGuideRequest(req);
        return ResponseEntity.status(201)
                .body(new ApiResponse("Request Sent", "SUCCESS"));
    }

    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse> createBooking(@RequestBody Booking b)
    {
        service.createBooking(b);
        return ResponseEntity.status(201)
                .body(new ApiResponse("Booking Created", "SUCCESS"));
    }

    @GetMapping("/bookings/{touristId}")
    public ResponseEntity<List<Booking>> getBookings(@PathVariable int touristId)
    {
        return ResponseEntity.ok(service.getBookingsByTourist(touristId));
    }

    @PutMapping("/bookings/cancel/{id}")
    public ResponseEntity<ApiResponse> cancelBooking(@PathVariable int id)
    {
        service.cancelBooking(id);
        return ResponseEntity.ok(new ApiResponse("Cancelled", "SUCCESS"));
    }

    @GetMapping("/dashboard/{touristId}")
    public ResponseEntity<Map<String, Object>> dashboard(@PathVariable int touristId)
    {
        return ResponseEntity.ok(service.getDashboardData(touristId));
    }
}