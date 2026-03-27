package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Booking;
import com.klef.fsad.sdp.service.BookingService;

@RestController
@RequestMapping("bookingapi")
@CrossOrigin("*")
public class BookingController 
{
 @Autowired
 private BookingService service;

 // TOURIST

 @PostMapping("/create")
 public String create(@RequestBody Booking b)
 {
  return service.createBooking(b);
 }

 @GetMapping("/tourist/{id}")
 public List<Booking> touristBookings(@PathVariable int id)
 {
  return service.getTouristBookings(id);
 }

 // HOST

 @GetMapping("/host/{id}")
 public List<Booking> hostBookings(@PathVariable int id)
 {
  return service.getHostBookings(id);
 }

 @PutMapping("/confirm/{id}")
 public String confirm(@PathVariable int id)
 {
  return service.confirmBooking(id);
 }

 @PutMapping("/reject/{id}")
 public String reject(@PathVariable int id)
 {
  return service.rejectBooking(id);
 }

 @PutMapping("/complete/{id}")
 public String complete(@PathVariable int id)
 {
  return service.completeBooking(id);
 }

 // PAYMENT

 @PutMapping("/payment/{id}/{status}")
 public String payment(@PathVariable int id,@PathVariable String status)
 {
  return service.updatePayment(id, status);
 }

 // ADMIN

 @GetMapping("/all")
 public List<Booking> all()
 {
  return service.getAllBookings();
 }

 @GetMapping("/status/{status}")
 public List<Booking> byStatus(@PathVariable String status)
 {
  return service.getByStatus(status);
 }

 @GetMapping("/paymentstatus/{status}")
 public List<Booking> byPayment(@PathVariable String status)
 {
  return service.getByPaymentStatus(status);
 }
}