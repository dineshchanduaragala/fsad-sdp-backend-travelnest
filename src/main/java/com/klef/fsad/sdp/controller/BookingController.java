package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.entity.Booking;
import com.klef.fsad.sdp.service.BookingService;

@RestController
@RequestMapping("bookingapi")
@CrossOrigin("*")
public class BookingController 
{
    @Autowired
    private BookingService service;

    // ===================== TOURIST =====================

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody Booking b)
    {
        String msg = service.createBooking(b);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    @GetMapping("/tourist/{id}")
    public ResponseEntity<List<Booking>> touristBookings(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getTouristBookings(id));
    }

    // ===================== HOST =====================

    @GetMapping("/host/{id}")
    public ResponseEntity<List<Booking>> hostBookings(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getHostBookings(id));
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<ApiResponse> confirm(@PathVariable int id)
    {
        String msg = service.confirmBooking(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<ApiResponse> reject(@PathVariable int id)
    {
        String msg = service.rejectBooking(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<ApiResponse> complete(@PathVariable int id)
    {
        String msg = service.completeBooking(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== PAYMENT =====================

    @PutMapping("/payment/{id}/{status}")
    public ResponseEntity<ApiResponse> payment(@PathVariable int id, @PathVariable String status)
    {
        String msg = service.updatePayment(id, status);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== ADMIN =====================

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> all()
    {
        return ResponseEntity.ok(service.getAllBookings());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> byStatus(@PathVariable String status)
    {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @GetMapping("/paymentstatus/{status}")
    public ResponseEntity<List<Booking>> byPayment(@PathVariable String status)
    {
        return ResponseEntity.ok(service.getByPaymentStatus(status));
    }
}