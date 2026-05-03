package com.klef.fsad.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.*;
import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.exception.UnauthorizedException;
import com.klef.fsad.sdp.repository.AdminRepository;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.*;
import com.klef.fsad.sdp.util.EmailService;
import com.klef.fsad.sdp.util.OTPService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController 
{
    @Autowired private AdminRepository adminRepo;
    @Autowired private HostService hostService;
    @Autowired private TouristService touristService;
    @Autowired private GuideService guideService;

    @Autowired private JwtUtil jwtUtil;
    @Autowired private EmailService emailService;
    @Autowired private OTPService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOTP(@RequestBody EmailRequest request)
    {
        String otp = otpService.generateOTP(request.getEmail());
        emailService.sendOTP(request.getEmail(), otp);

        return ResponseEntity.ok(new ApiResponse("OTP Sent Successfully", "SUCCESS"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOTP(@RequestBody OTPRequest request)
    {
        boolean isValid = otpService.validateOTP(request.getEmail(), request.getOtp());

        if (!isValid)
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Invalid or Expired OTP", "FAIL"));

        return ResponseEntity.ok(new ApiResponse("OTP Verified Successfully", "SUCCESS"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        String role = request.getRole().toUpperCase();

        try {

            switch (role)
            {
                case "ADMIN":

                    Admin admin = adminRepo.findById(request.getUsername()).orElse(null);

                    if (admin == null ||
                        !admin.getPassword().equals(request.getPassword()) ||
                        !String.valueOf(admin.getPin()).equals(String.valueOf(request.getPin())))
                    {
                        return ResponseEntity.status(401)
                                .body(new ApiResponse("Invalid Admin Credentials", "FAIL"));
                    }

                    String adminToken = jwtUtil.generateToken(admin.getUsername(), "ADMIN");

                    return ResponseEntity.ok(
                            new ApiResponse("Login Successful", "SUCCESS",
                                    Map.of("token", adminToken, "role", "ADMIN", "user", admin))
                    );

                case "HOST":
                case "TOURIST":
                case "GUIDE":

                    boolean validOtp = otpService.validateOTP(
                            request.getEmail(),
                            request.getOtp()
                    );

                    if (!validOtp)
                        return ResponseEntity.status(401)
                                .body(new ApiResponse("OTP Not Verified or Expired", "FAIL"));

                    otpService.clearOTP(request.getEmail());

                    if (role.equals("HOST"))
                    {
                        Host host = hostService.login(request.getEmail(), request.getPassword());

                        String token = jwtUtil.generateToken(host.getEmail(), "HOST");

                        return ResponseEntity.ok(
                                new ApiResponse("Login Successful", "SUCCESS",
                                        Map.of("token", token, "role", "HOST", "user", host))
                        );
                    }

                    if (role.equals("TOURIST"))
                    {
                        Tourist tourist = touristService.login(request.getEmail(), request.getPassword());

                        String token = jwtUtil.generateToken(tourist.getEmail(), "TOURIST");

                        return ResponseEntity.ok(
                                new ApiResponse("Login Successful", "SUCCESS",
                                        Map.of("token", token, "role", "TOURIST", "user", tourist))
                        );
                    }

                    if (role.equals("GUIDE"))
                    {
                        Guide guide = guideService.login(request.getEmail(), request.getPassword());

                        String token = jwtUtil.generateToken(guide.getEmail(), "GUIDE");

                        return ResponseEntity.ok(
                                new ApiResponse("Login Successful", "SUCCESS",
                                        Map.of("token", token, "role", "GUIDE", "user", guide))
                        );
                    }

                default:
                    return ResponseEntity.badRequest()
                            .body(new ApiResponse("Invalid Role Selected", "FAIL"));
            }

        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse(ex.getMessage(), "FAIL"));
        }
    }
}