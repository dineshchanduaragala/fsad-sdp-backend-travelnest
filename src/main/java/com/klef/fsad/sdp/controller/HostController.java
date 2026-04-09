package com.klef.fsad.sdp.controller;

import java.time.LocalDate;  // ✅ ADDED FOR DATE PARSING
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.entity.Booking;
import com.klef.fsad.sdp.entity.Host;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.HostService;

@RestController
@RequestMapping("hostapi")
@CrossOrigin("*")
public class HostController 
{
    @Autowired
    private HostService service;

    @Autowired
    private JwtUtil jwtUtil;

    // ===================== REGISTER =====================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody Host h)
    {
        String msg = service.registerHost(h);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest req)
    {
        Host user = service.login(req.getEmail(), req.getPassword());

        // ❌ INVALID OR NOT APPROVED
        if (user == null)
        {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid Credentials or Not Approved", "FAIL"));
        }

        // ✅ GENERATE TOKEN
        String token = jwtUtil.generateToken(user.getEmail(), "HOST");

        return ResponseEntity.ok(
            new ApiResponse("Login Successful", "SUCCESS",
                Map.of(
                    "token", token,
                    "user", user
                )
            )
        );
    }

    // ===================== VIEW ALL =====================
    @GetMapping("/all")
    public ResponseEntity<List<Host>> getAll()
    {
        return ResponseEntity.ok(service.getAllHosts());
    }

    // ===================== UPDATE =====================
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody Host h)
    {
        String msg = service.updateHost(h);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== DELETE =====================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id)
    {
        String msg = service.deleteHost(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ✅ FIXED OFFLINE BOOKING ENDPOINT (COMPILER ERROR SOLVED)
    @PostMapping("/offlinebooking")
    public ResponseEntity<ApiResponse> offlineBooking(
        @RequestParam String guestName,
        @RequestParam String phone,
        @RequestParam String checkInStr,    // ✅ Renamed for clarity
        @RequestParam String checkOutStr,   // ✅ Renamed for clarity
        @RequestParam int days,
        @RequestParam double amount,
        @RequestParam String paymentMode,
        @RequestParam int hostId
    ) {
        try {
            Booking b = new Booking();
            b.setGuestName(guestName);
            b.setPhone(phone);
            
            // ✅ FIXED: String → LocalDate conversion
            b.setCheckIn(LocalDate.parse(checkInStr));
            b.setCheckOut(LocalDate.parse(checkOutStr));
            
            b.setDays(days);
            b.setAmount(amount);
            b.setPaymentMode(paymentMode);
            b.setHostId(hostId);
            b.setBookingStatus("CONFIRMED");  // Auto-confirm offline
            b.setPaymentStatus("PAID");

            String msg = service.createOfflineBooking(b);

            return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Offline booking failed: " + e.getMessage(), "FAIL"));
        }
    }
}