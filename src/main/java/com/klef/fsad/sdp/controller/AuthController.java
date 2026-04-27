package com.klef.fsad.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.dto.EmailRequest;
import com.klef.fsad.sdp.dto.OTPRequest;
import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.*;
import com.klef.fsad.sdp.util.EmailService;
import com.klef.fsad.sdp.util.OTPService;

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

    // ✅ NEW SERVICES
    @Autowired
    private EmailService emailService;

    @Autowired
    private OTPService otpService;

    // =========================================================
    // ✅ SEND OTP
    // =========================================================
    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOTP(@RequestBody EmailRequest request)
    {
        String otp = otpService.generateOTP(request.getEmail());
        emailService.sendOTP(request.getEmail(), otp);

        return ResponseEntity.ok(
                new ApiResponse("OTP Sent Successfully", "SUCCESS")
        );
    }

    // =========================================================
    // ✅ VERIFY OTP
    // =========================================================
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOTP(@RequestBody OTPRequest request)
    {
        boolean isValid = otpService.validateOTP(
                request.getEmail(),
                request.getOtp()
        );

        if (!isValid)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Invalid or Expired OTP", "FAIL"));

        return ResponseEntity.ok(
                new ApiResponse("OTP Verified Successfully", "SUCCESS")
        );
    }

    // =========================================================
    // ✅ UNIVERSAL LOGIN WITH OTP VALIDATION
    // =========================================================
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

                // 🔐 OTP VALIDATION
                if (!otpService.validateOTP(request.getEmail(), request.getOtp()))
                {
                    return ResponseEntity.status(401)
                            .body(new ApiResponse("OTP Not Verified or Expired", "FAIL"));
                }

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

                // 🔐 OTP VALIDATION
                if (!otpService.validateOTP(request.getEmail(), request.getOtp()))
                {
                    return ResponseEntity.status(401)
                            .body(new ApiResponse("OTP Not Verified or Expired", "FAIL"));
                }

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

                // 🔐 OTP VALIDATION
                if (!otpService.validateOTP(request.getEmail(), request.getOtp()))
                {
                    return ResponseEntity.status(401)
                            .body(new ApiResponse("OTP Not Verified or Expired", "FAIL"));
                }

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