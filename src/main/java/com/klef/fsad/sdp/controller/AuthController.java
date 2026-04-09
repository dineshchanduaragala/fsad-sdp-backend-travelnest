package com.klef.fsad.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController 
{
    @Autowired
    private AdminService adminService;

    @Autowired
    private HostService hostService;

    @Autowired
    private TouristService touristService;

    @Autowired
    private GuideService guideService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ UNIVERSAL LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request)
    {
        String role = request.getRole().toUpperCase();

        switch (role)
        {
            // ================= ADMIN =================
            case "ADMIN":
                Admin admin = adminService.verifyAdminLogin(
                        request.getUsername(),
                        request.getPassword(),
                        request.getPin()
                );

                if (admin == null)
                    return ResponseEntity.status(401)
                            .body(new ApiResponse("Invalid Admin Credentials", "FAIL"));

                String adminToken = jwtUtil.generateToken(admin.getUsername(), "ADMIN");

                return ResponseEntity.ok(
                        new ApiResponse("Login Successful", "SUCCESS",
                                Map.of("token", adminToken, "user", admin))
                );

            // ================= HOST =================
            case "HOST":
                Host host = hostService.login(
                        request.getEmail(),
                        request.getPassword()
                );

                if (host == null)
                    return ResponseEntity.status(401)
                            .body(new ApiResponse("Invalid Host Credentials / Not Approved", "FAIL"));

                String hostToken = jwtUtil.generateToken(host.getEmail(), "HOST");

                return ResponseEntity.ok(
                        new ApiResponse("Login Successful", "SUCCESS",
                                Map.of("token", hostToken, "user", host))
                );

            // ================= TOURIST =================
            case "TOURIST":
                Tourist tourist = touristService.login(
                        request.getEmail(),
                        request.getPassword()
                );

                if (tourist == null)
                    return ResponseEntity.status(401)
                            .body(new ApiResponse("Invalid Tourist Credentials", "FAIL"));

                String touristToken = jwtUtil.generateToken(tourist.getEmail(), "TOURIST");

                return ResponseEntity.ok(
                        new ApiResponse("Login Successful", "SUCCESS",
                                Map.of("token", touristToken, "user", tourist))
                );

            // ================= GUIDE =================
            case "GUIDE":
                Guide guide = guideService.login(
                        request.getEmail(),
                        request.getPassword()
                );

                if (guide == null)
                    return ResponseEntity.status(401)
                            .body(new ApiResponse("Invalid Guide Credentials / Not Approved", "FAIL"));

                String guideToken = jwtUtil.generateToken(guide.getEmail(), "GUIDE");

                return ResponseEntity.ok(
                        new ApiResponse("Login Successful", "SUCCESS",
                                Map.of("token", guideToken, "user", guide))
                );

            default:
                return ResponseEntity.badRequest()
                        .body(new ApiResponse("Invalid Role Selected", "FAIL"));
        }
    }
}