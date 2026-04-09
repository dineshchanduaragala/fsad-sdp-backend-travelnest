package com.klef.fsad.sdp.controller;

import java.util.List;
import java.util.Map;  // ✅ ADD

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.dto.AuthResponse;
import com.klef.fsad.sdp.dto.LoginRequest;
import com.klef.fsad.sdp.entity.Guide;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.GuideService;

@RestController
@RequestMapping("guideapi")
@CrossOrigin("*")
public class GuideController 
{
    @Autowired
    private GuideService service;

    @Autowired
    private JwtUtil jwtUtil;

    // ===================== REGISTER =====================
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody Guide g)
    {
        String msg = service.registerGuide(g);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== LOGIN =====================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req)
    {
        Guide user = service.login(req.getEmail(), req.getPassword());

        if (user == null)
        {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid Credentials or Not Approved", "FAIL"));
        }

        String token = jwtUtil.generateToken(user.getEmail(), "GUIDE");

        return ResponseEntity.ok(
            new AuthResponse(token, "GUIDE", user)
        );
    }

    // ===================== VIEW APPROVED GUIDES =====================
    @GetMapping("/all")
    public ResponseEntity<List<Guide>> getAll()
    {
        return ResponseEntity.ok(service.getApprovedGuides());
    }

    // ===================== UPDATE PROFILE =====================
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody Map<String, Object> data) {
        try {
            // ✅ EXTRACT ID FIRST
            if (!data.containsKey("id")) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse("ID required", "ERROR"));
            }
            
            int id = Integer.parseInt(data.get("id").toString());
            Guide existing = service.getGuideById(id);
            
            if (existing == null) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse("Guide not found: ID " + id, "ERROR"));
            }
            
            // ✅ MANUAL SAFE UPDATES
            if (data.containsKey("name") && data.get("name") != null) {
                existing.setName(data.get("name").toString());
            }
            if (data.containsKey("phone") && data.get("phone") != null) {
                existing.setPhone(data.get("phone").toString());
            }
            if (data.containsKey("experience") && data.get("experience") != null) {
                existing.setExperience(data.get("experience").toString());
            }
            if (data.containsKey("languages") && data.get("languages") != null) {
                existing.setLanguages(data.get("languages").toString());
            }
            if (data.containsKey("location") && data.get("location") != null) {
                existing.setLocation(data.get("location").toString());
            }
            if (data.containsKey("available")) {
                existing.setAvailable(Boolean.parseBoolean(data.get("available").toString()));
            }
            
            // ✅ Preserve approval
            boolean wasApproved = existing.isApproved();
            service.updateGuide(existing);  // Reuse service
            
            return ResponseEntity.ok(new ApiResponse("Profile Updated Successfully", "SUCCESS"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("Update failed: " + e.getMessage(), "ERROR"));
        }
    }
    
 // ===================== GET GUIDE BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id)
    {
        Guide guide = service.getGuideById(id);

        if (guide == null)
        {
            return ResponseEntity.status(404)
                    .body(new ApiResponse("Guide Not Found", "FAIL"));
        }

        return ResponseEntity.ok(
                new ApiResponse("Guide Found", "SUCCESS", guide)
        );
    }

    // ===================== DELETE =====================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id)
    {
        String msg = service.deleteGuide(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== AVAILABILITY =====================
    @PutMapping("/availability/{id}/{status}")
    public ResponseEntity<ApiResponse> updateAvailability(
            @PathVariable int id,
            @PathVariable boolean status)
    {
        String msg = service.toggleAvailability(id, status);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }
}