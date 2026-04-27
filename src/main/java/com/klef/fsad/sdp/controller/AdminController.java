package com.klef.fsad.sdp.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.*;
import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.AdminService;

@RestController
@RequestMapping("/adminapi")
@CrossOrigin("*")
public class AdminController
{
    @Autowired
    private AdminService service;

    @Autowired
    private JwtUtil jwtUtil;

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest req)
    {
        Admin user = service.verifyAdminLogin(
                req.getUsername(),
                req.getPassword(),
                req.getPin()
        );

        if (user == null)
        {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid Credentials", "FAIL"));
        }

        String token = jwtUtil.generateToken(user.getUsername(), "ADMIN");

        return ResponseEntity.ok(
                new ApiResponse("Login Success", "SUCCESS",
                        Map.of(
                                "token", token,
                                "role", "ADMIN",
                                "user", user
                        )
                )
        );
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public ApiResponse dashboard()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", Map.of(
                "tourists", service.getTotalTourists(),
                "hosts", service.getTotalHosts(),
                "guides", service.getTotalGuides(),
                "homestays", service.getTotalHomestays(),
                "attractions", service.getTotalAttractions(),
                "bookings", service.getTotalBookings()
        ));
    }

    // TOURISTS
    @GetMapping("/tourists")
    public ApiResponse tourists()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getAllTourists());
    }
    
 // ================= TOURISTS =================

    @PutMapping("/tourists")
    public ApiResponse updateTourist(@RequestBody Tourist t)
    {
        return new ApiResponse(service.updateTourist(t), "SUCCESS");
    }

    @DeleteMapping("/tourists/{id}")
    public ApiResponse deleteTourist(@PathVariable int id)
    {
        return new ApiResponse(service.deleteTourist(id), "SUCCESS");
    }

    // HOSTS
    @GetMapping("/hosts")
    public ApiResponse hosts()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getAllHosts());
    }

    @GetMapping("/hosts/pending")
    public ApiResponse pendingHosts()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getPendingHosts());
    }

    @PutMapping("/hosts/approve/{id}")
    public ApiResponse approveHost(@PathVariable int id)
    {
        return new ApiResponse(service.approveHost(id), "SUCCESS");
    }

    @PutMapping("/hosts/reject/{id}")
    public ApiResponse rejectHost(@PathVariable int id)
    {
        return new ApiResponse(service.rejectHost(id), "SUCCESS");
    }

    @DeleteMapping("/hosts/{id}")
    public ApiResponse deleteHost(@PathVariable int id)
    {
        return new ApiResponse(service.deleteHost(id), "SUCCESS");
    }

    // ✅ FIXED UPDATE HOST
    @PutMapping("/hosts/update")
    public ResponseEntity<ApiResponse> updateHost(@RequestBody Host h)
    {
        try {
            String msg = service.updateHost(h);
            return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("Update Failed", "ERROR"));
        }
    }

    // GUIDES
    @GetMapping("/guides")
    public ApiResponse guides()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getAllGuides());
    }

    @GetMapping("/guides/pending")
    public ApiResponse pendingGuides()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getPendingGuides());
    }

    @PutMapping("/guides/approve/{id}")
    public ApiResponse approveGuide(@PathVariable int id)
    {
        return new ApiResponse(service.approveGuide(id), "SUCCESS");
    }

    @PutMapping("/guides/reject/{id}")
    public ApiResponse rejectGuide(@PathVariable int id)
    {
        return new ApiResponse(service.rejectGuide(id), "SUCCESS");
    }

    // HOMESTAYS
    @GetMapping("/homestays")
    public ApiResponse homestays()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getAllHomestays());
    }

    @PostMapping("/homestays")
    public ApiResponse addHomestay(@RequestBody Homestay h)
    {
        h.setApproved(true);
        return new ApiResponse(service.addHomestay(h), "SUCCESS");
    }

    @PutMapping("/homestays")
    public ApiResponse updateHomestay(@RequestBody Homestay h)
    {
        return new ApiResponse(service.updateHomestay(h), "SUCCESS");
    }

    @PutMapping("/homestays/approve/{id}")
    public ApiResponse approveHomestay(@PathVariable int id)
    {
        return new ApiResponse(service.approveHomestay(id), "SUCCESS");
    }

    @PutMapping("/homestays/reject/{id}")
    public ApiResponse rejectHomestay(@PathVariable int id)
    {
        return new ApiResponse(service.rejectHomestay(id), "SUCCESS");
    }

    @DeleteMapping("/homestays/{id}")
    public ApiResponse deleteHomestay(@PathVariable int id)
    {
        return new ApiResponse(service.deleteHomestay(id), "SUCCESS");
    }

    // ATTRACTIONS
    @GetMapping("/attractions")
    public ApiResponse attractions()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getAllAttractions());
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

    // BOOKINGS
    @GetMapping("/bookings")
    public ApiResponse bookings()
    {
        return new ApiResponse("SUCCESS", "SUCCESS", service.getAllBookings());
    }
}