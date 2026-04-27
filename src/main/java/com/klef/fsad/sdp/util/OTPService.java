package com.klef.fsad.sdp.util;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService 
{
    private Map<String, String> otpStorage = new HashMap<>();
    private Map<String, Long> otpExpiry = new HashMap<>();

    // Generate OTP
    public String generateOTP(String email) 
    {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        otpStorage.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + (5 * 60 * 1000)); // 5 mins

        return otp;
    }

    // Validate OTP
    public boolean validateOTP(String email, String otp) 
    {
        if (!otpStorage.containsKey(email))
            return false;

        if (System.currentTimeMillis() > otpExpiry.get(email))
        {
            otpStorage.remove(email);
            otpExpiry.remove(email);
            return false;
        }

        return otp.equals(otpStorage.get(email));
    }
}