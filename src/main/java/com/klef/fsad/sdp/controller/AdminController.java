package com.klef.fsad.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.service.AdminService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminapi")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService service;

    
    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody Admin loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();
            Integer pin = loginRequest.getPin();

            if (username == null || password == null || pin == null) {
                return ResponseEntity.badRequest().body("All fields are required");
            }

            Admin admin = service.validateAdminLogin(username, password, pin);

            if (admin == null) {
                return ResponseEntity.status(401).body("Invalid Credentials");
            }

            return ResponseEntity.ok(admin);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Login Failed");
        }
    }
    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard() {
        try {
            Map<String, Long> stats = service.getDashboardStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error loading dashboard");
        }
    }

    // ================= TOURISTS =================
    @GetMapping("/tourists")
    public ResponseEntity<List<Tourist>> getAllTourists() {
        return ResponseEntity.ok(service.getAllTourists());
    }

    @PutMapping("/tourists")
    public ResponseEntity<?> updateTourist(@RequestBody Tourist t) {
        return ResponseEntity.ok(service.updateTourist(t));
    }

    @DeleteMapping("/tourists/{id}")
    public ResponseEntity<?> deleteTourist(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteTourist(id));
    }

    // ================= HOSTS =================
    @GetMapping("/hosts")
    public ResponseEntity<List<Host>> getHosts() {
        return ResponseEntity.ok(service.getAllHosts());
    }

    @GetMapping("/hosts/pending")
    public ResponseEntity<List<Host>> getPendingHosts() {
        return ResponseEntity.ok(service.getPendingHosts());
    }

    @PutMapping("/hosts")
    public ResponseEntity<?> updateHost(@RequestBody Host h) {
        return ResponseEntity.ok(service.updateHost(h));
    }

    @DeleteMapping("/hosts/{id}")
    public ResponseEntity<?> deleteHost(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteHost(id));
    }

    @PutMapping("/hosts/approve/{id}")
    public ResponseEntity<?> approveHost(@PathVariable int id) {
        return ResponseEntity.ok(service.approveHost(id));
    }

    @PutMapping("/hosts/reject/{id}")
    public ResponseEntity<?> rejectHost(@PathVariable int id) {
        return ResponseEntity.ok(service.rejectHost(id));
    }

    // ================= GUIDES =================
    @GetMapping("/guides")
    public ResponseEntity<List<Guide>> getGuides() {
        return ResponseEntity.ok(service.getAllGuides());
    }

    @GetMapping("/guides/pending")
    public ResponseEntity<List<Guide>> getPendingGuides() {
        return ResponseEntity.ok(service.getPendingGuides());
    }

    @PutMapping("/guides")
    public ResponseEntity<?> updateGuide(@RequestBody Guide g) {
        return ResponseEntity.ok(service.updateGuide(g));
    }

    @DeleteMapping("/guides/{id}")
    public ResponseEntity<?> deleteGuide(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteGuide(id));
    }

    @PutMapping("/guides/approve/{id}")
    public ResponseEntity<?> approveGuide(@PathVariable int id) {
        return ResponseEntity.ok(service.approveGuide(id));
    }

    @PutMapping("/guides/reject/{id}")
    public ResponseEntity<?> rejectGuide(@PathVariable int id) {
        return ResponseEntity.ok(service.rejectGuide(id));
    }

    // ================= ATTRACTIONS =================
    @GetMapping("/attractions")
    public ResponseEntity<List<Attraction>> getAttractions() {
        return ResponseEntity.ok(service.getAllAttractions());
    }

    @GetMapping("/attractions/{id}")
    public ResponseEntity<?> getAttractionById(@PathVariable int id) {
        return ResponseEntity.ok(service.getAttractionById(id));
    }

    @PostMapping("/attractions")
    public ResponseEntity<?> addAttraction(
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam(required = false) String description,
            @RequestParam String entryFee,
            @RequestParam String timings,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {
            double fee = Double.parseDouble(entryFee);

            if (fee < 0) {
                return ResponseEntity.badRequest().body("Entry fee must be positive");
            }

            Attraction a = new Attraction();
            a.setName(name.trim());
            a.setLocation(location.trim());
            a.setDescription(description != null ? description.trim() : "");
            a.setEntryFee(fee);
            a.setTimings(timings.trim());

            if (image != null && !image.isEmpty()) {
                a.setImage(image.getBytes());
                a.setImageType(image.getContentType());
            }

            return ResponseEntity.ok(service.addAttraction(a));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error adding attraction");
        }
    }

    @PutMapping("/attractions")
    public ResponseEntity<?> updateAttraction(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String description,
            @RequestParam String entryFee,
            @RequestParam String timings,
            @RequestParam(required = false) MultipartFile image
    ) {
        try {
            double fee = Double.parseDouble(entryFee);

            Attraction a = new Attraction();
            a.setId(id);
            a.setName(name.trim());
            a.setLocation(location.trim());
            a.setDescription(description.trim());
            a.setEntryFee(fee);
            a.setTimings(timings.trim());

            if (image != null && !image.isEmpty()) {
                a.setImage(image.getBytes());
                a.setImageType(image.getContentType());
            }

            return ResponseEntity.ok(service.updateAttraction(a));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating attraction");
        }
    }

    @DeleteMapping("/attractions/{id}")
    public ResponseEntity<?> deleteAttraction(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteAttraction(id));
    }

    // ================= BOOKINGS =================
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(service.getAllBookings());
    }

    @PutMapping("/bookings/confirm/{id}")
    public ResponseEntity<?> confirmBooking(@PathVariable int id) {
        return ResponseEntity.ok(service.updateBookingStatus(id, "CONFIRMED"));
    }

    @PutMapping("/bookings/reject/{id}")
    public ResponseEntity<?> rejectBooking(@PathVariable int id) {
        return ResponseEntity.ok(service.updateBookingStatus(id, "REJECTED"));
    }

    @PutMapping("/bookings/complete/{id}")
    public ResponseEntity<?> completeBooking(@PathVariable int id) {
        return ResponseEntity.ok(service.updateBookingStatus(id, "COMPLETED"));
    }

    // ================= HOMESTAYS =================
    @GetMapping("/homestays")
    public ResponseEntity<List<Homestay>> getHomestays() {
        return ResponseEntity.ok(service.getAllHomestays());
    }

    @DeleteMapping("/homestays/{id}")
    public ResponseEntity<?> deleteHomestay(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteHomestay(id));
    }
    
    @PutMapping("/homestays")
    public ResponseEntity<?> updateHomestay(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String description,
            @RequestParam String price,
            @RequestParam String facilities,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) MultipartFile qr
    ) {
        try {
            double p = Double.parseDouble(price);

            Homestay existing = service.getHomestayById(id);
            if (existing == null) {
                return ResponseEntity.badRequest().body("Homestay not found");
            }

            existing.setName(name.trim());
            existing.setLocation(location.trim());
            existing.setDescription(description.trim());
            existing.setPrice(p);
            existing.setFacilities(facilities.trim());

            if (image != null && !image.isEmpty()) {
                existing.setImage(image.getBytes());
                existing.setImageType(image.getContentType());
            }

            if (qr != null && !qr.isEmpty()) {
                existing.setQrImage(qr.getBytes());
                existing.setQrImageType(qr.getContentType());
            }

            return ResponseEntity.ok(service.updateHomestay(existing));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating homestay");
        }
    }
}