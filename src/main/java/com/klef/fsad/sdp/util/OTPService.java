package com.klef.fsad.sdp.util;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OTPService 
{
    // 🔥 THREAD-SAFE MAPS
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, Long> otpExpiry = new ConcurrentHashMap<>();

    // ================= GENERATE OTP =================
    public String generateOTP(String email) 
    {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        otpStorage.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + (5 * 60 * 1000)); // 5 minutes

        System.out.println("OTP GENERATED -> EMAIL: " + email + " OTP: " + otp);

        return otp;
    }

    // ================= VALIDATE OTP =================
    public boolean validateOTP(String email, String otp) 
    {
        String storedOtp = otpStorage.get(email);
        Long expiryTime = otpExpiry.get(email);

        System.out.println("OTP VALIDATION -> EMAIL: " + email);
        System.out.println("ENTERED OTP: " + otp);
        System.out.println("STORED OTP: " + storedOtp);

        // ❌ OTP NOT FOUND
        if (storedOtp == null || expiryTime == null) {
            System.out.println("OTP NOT FOUND");
            return false;
        }

        // ❌ EXPIRED
        if (System.currentTimeMillis() > expiryTime) {
            System.out.println("OTP EXPIRED");

            otpStorage.remove(email);
            otpExpiry.remove(email);

            return false;
        }

        // ❌ MISMATCH
        if (!storedOtp.equals(otp.trim())) {
            System.out.println("OTP MISMATCH");
            return false;
        }

        System.out.println("OTP VERIFIED SUCCESS");

        return true;
    }

    // ================= CLEAR OTP =================
    public void clearOTP(String email) 
    {
        otpStorage.remove(email);
        otpExpiry.remove(email);

        System.out.println("OTP CLEARED for: " + email);
    }
}