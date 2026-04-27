package com.klef.fsad.sdp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService 
{
    @Autowired
    private JavaMailSender mailSender;

    public void sendOTP(String toEmail, String otp) 
    {
        try 
        {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("🔐 TravelNest OTP Verification");

            // ✅ HTML EMAIL TEMPLATE
            String htmlContent = 
                "<div style='font-family: Arial, sans-serif; padding:20px; background:#f4f4f4;'>"
              + "  <div style='max-width:600px; margin:auto; background:white; border-radius:10px; overflow:hidden;'>"
              
              + "    <img src='https://static.toiimg.com/thumb/112426053/Charming-homestays-in-India.jpg?width=636&height=358&resize=4' "
              + "         style='width:100%; height:auto;' />"
              
              + "    <div style='padding:20px;'>"
              + "      <h2 style='color:#2c3e50;'>Welcome to TravelNest 🌍</h2>"
              + "      <p style='font-size:16px;'>Use the OTP below to continue your login:</p>"
              
              + "      <div style='text-align:center; margin:20px 0;'>"
              + "        <span style='font-size:28px; font-weight:bold; letter-spacing:5px; color:#27ae60;'>"
              + otp
              + "        </span>"
              + "      </div>"
              
              + "      <p style='color:#555;'>⏳ This OTP is valid for <b>5 minutes</b>.</p>"
              + "      <p style='color:#888; font-size:14px;'>If you didn’t request this, please ignore this email.</p>"
              
              + "      <hr/>"
              + "      <p style='text-align:center; color:#aaa;'>© 2026 TravelNest</p>"
              + "    </div>"
              
              + "  </div>"
              + "</div>";

            helper.setText(htmlContent, true); // ✅ enable HTML

            mailSender.send(message);
        } 
        catch (MessagingException e) 
        {
            e.printStackTrace();
        }
    }
}