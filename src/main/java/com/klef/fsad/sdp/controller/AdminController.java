package com.klef.fsad.sdp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.*;
import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.AdminService;

@RestController
@RequestMapping("adminapi")
@CrossOrigin("*")
public class AdminController
{
    @Autowired
    private AdminService service;

    @Autowired
    private JwtUtil jwtUtil;

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req)
    {
        // ✅ FIXED (username, not email)
        Admin user = service.verifyAdminLogin(
                req.getUsername(),
                req.getPassword(),
                req.getPin()
        );

        if (user == null)
        {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid Admin Credentials", "FAIL"));
        }

        String token = jwtUtil.generateToken(user.getUsername(), "ADMIN");

        return ResponseEntity.ok(new AuthResponse(token, "ADMIN", user));
    }

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public ApiResponse dashboard()
    {
        return new ApiResponse("Dashboard Data", "SUCCESS", Map.of(
                "tourists", service.getTotalTourists(),
                "hosts", service.getTotalHosts(),
                "guides", service.getTotalGuides(),
                "homestays", service.getTotalHomestays(),
                "attractions", service.getTotalAttractions(),
                "bookings", service.getTotalBookings()
        ));
    }

    // ================= TOURISTS =================
    @GetMapping("/tourists")
    public ApiResponse getAllTourists()
    {
        return new ApiResponse(
                "Tourists List",
                "SUCCESS",
                service.getAllTourists()
        );
    }

    // ================= HOSTS =================
    @GetMapping("/hosts")
    public ApiResponse allHosts()
    {
        return new ApiResponse("Hosts List", "SUCCESS", service.getAllHosts());
    }

    @GetMapping("/hosts/pending")
    public ApiResponse pendingHosts()
    {
        return new ApiResponse("Pending Hosts", "SUCCESS", service.getPendingHosts());
    }

    @PostMapping("/hosts/approve/{id}")
    public ApiResponse approveHost(@PathVariable int id)
    {
        return new ApiResponse(service.approveHost(id), "SUCCESS");
    }

    @PostMapping("/hosts/reject/{id}")
    public ApiResponse rejectHost(@PathVariable int id)
    {
        return new ApiResponse(service.rejectHost(id), "SUCCESS");
    }

    // ✅ CORRECT UPDATE HOST (FINAL FIX)
    @PutMapping("/hosts/update")
    public ApiResponse updateHost(@RequestBody Host h)
    {
        return new ApiResponse(service.updateHost(h), "SUCCESS");
    }

    // ================= GUIDES =================
    @GetMapping("/guides")
    public ApiResponse allGuides()
    {
        return new ApiResponse("Guides List", "SUCCESS", service.getAllGuides());
    }

    @GetMapping("/guides/pending")
    public ApiResponse pendingGuides()
    {
        return new ApiResponse("Pending Guides", "SUCCESS", service.getPendingGuides());
    }

    @PostMapping("/guides/approve/{id}")
    public ApiResponse approveGuide(@PathVariable int id)
    {
        return new ApiResponse(service.approveGuide(id), "SUCCESS");
    }

    @PostMapping("/guides/reject/{id}")
    public ApiResponse rejectGuide(@PathVariable int id)
    {
        return new ApiResponse(service.rejectGuide(id), "SUCCESS");
    }

    // ================= HOMESTAYS =================
    @GetMapping("/homestays")
    public ApiResponse allHomestays()
    {
        return new ApiResponse("Homestays", "SUCCESS", service.getAllHomestays());
    }

    @GetMapping("/homestays/pending")
    public ApiResponse pendingHomestays()
    {
        return new ApiResponse("Pending Homestays", "SUCCESS", service.getPendingHomestays());
    }

    @PostMapping("/homestays")
    public ApiResponse addHomestay(@RequestBody Homestay h)
    {
        return new ApiResponse(service.addHomestay(h), "SUCCESS");
    }

    @PutMapping("/homestays")
    public ApiResponse updateHomestay(@RequestBody Homestay h)
    {
        return new ApiResponse(service.updateHomestay(h), "SUCCESS");
    }

    @PostMapping("/homestays/approve/{id}")
    public ApiResponse approveHomestay(@PathVariable int id)
    {
        return new ApiResponse(service.approveHomestay(id), "SUCCESS");
    }

    @PostMapping("/homestays/reject/{id}")
    public ApiResponse rejectHomestay(@PathVariable int id)
    {
        return new ApiResponse(service.rejectHomestay(id), "SUCCESS");
    }

    @DeleteMapping("/homestays/{id}")
    public ApiResponse deleteHomestay(@PathVariable int id)
    {
        return new ApiResponse(service.deleteHomestay(id), "SUCCESS");
    }

    // ================= ATTRACTIONS =================
    @GetMapping("/attractions")
    public ApiResponse attractions()
    {
        return new ApiResponse("Attractions", "SUCCESS", service.getAllAttractions());
    }

    @PostMapping("/attractions")
    public ApiResponse addAttraction(@RequestBody Attraction a)
    {
        return new ApiResponse(service.addAttraction(a), "SUCCESS");
    }

    @PutMapping("/attractions")
    public ApiResponse updateAttraction(@RequestBody Attraction a)
    {
        return new ApiResponse(service.updateAttraction(a), "SUCCESS");
    }

    @DeleteMapping("/attractions/{id}")
    public ApiResponse deleteAttraction(@PathVariable int id)
    {
        return new ApiResponse(service.deleteAttraction(id), "SUCCESS");
    }

    // ================= BOOKINGS =================
    @GetMapping("/bookings")
    public ApiResponse bookings()
    {
        return new ApiResponse("Bookings", "SUCCESS", service.getAllBookings());
    }
    
 // ================= DELETE HOST =================
    @DeleteMapping("/hosts/delete/{id}")
    public ApiResponse deleteHost(@PathVariable int id)
    {
        return new ApiResponse(service.deleteHost(id), "SUCCESS");
    }
}