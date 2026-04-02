package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.service.AdminService;

@RestController
@RequestMapping("adminapi")
@CrossOrigin("*")
public class AdminController 
{
 @Autowired
 private AdminService service;

 //LOGIN
 @PostMapping("/login")
 public ResponseEntity<?> login(@RequestBody Admin admin) {

     Admin user = service.verifyAdminLogin(
         admin.getUsername(),
         admin.getPassword(),
         admin.getPin()
     );

     if(user != null) {
         return ResponseEntity.ok(user);
     } else {
         return ResponseEntity.status(401).body("Invalid Credentials or PIN");
     }
 }

 // DASHBOARD
 @GetMapping("/dashboard")
 public Object dashboard() {
  return new Object() {
   public long tourists = service.getTotalTourists();
   public long hosts = service.getTotalHosts();
   public long guides = service.getTotalGuides();
   public long homestays = service.getTotalHomestays();
   public long attractions = service.getTotalAttractions();
   public long bookings = service.getTotalBookings();
  };
 }

 // HOSTS
 @GetMapping("/hosts")
 public List<Host> allHosts() 
 { 
	 return service.getAllHosts();
 }

 @GetMapping("/hosts/pending")
 public List<Host> pendingHosts() 
 { 
	 return service.getPendingHosts();
 }

 @PostMapping("/hosts/approve/{id}")
 public String approveHost(@PathVariable int id) 
 { 
	 return service.approveHost(id);
 }

 @PostMapping("/hosts/reject/{id}")
 public String rejectHost(@PathVariable int id) 
 { 
	 return service.rejectHost(id);
 }

 // GUIDES
 @GetMapping("/guides")
 public List<Guide> allGuides() 
 { 
	 return service.getAllGuides();
 }

 @GetMapping("/guides/pending")
 public List<Guide> pendingGuides() 
 { 
	 return service.getPendingGuides();
 }

 @PostMapping("/guides/approve/{id}")
 public String approveGuide(@PathVariable int id) 
 { 
	 return service.approveGuide(id);
 }

 @PostMapping("/guides/reject/{id}")
 public String rejectGuide(@PathVariable int id) 
 { 
	 return service.rejectGuide(id);
 }

 // HOMESTAYS
 @GetMapping("/homestays")
 public List<Homestay> allHomestays() 
 { 
	 return service.getAllHomestays();
 }

 @GetMapping("/homestays/pending")
 public List<Homestay> pendingHomestays() 
 { 
	 return service.getPendingHomestays();
 }

 @PostMapping("/homestays")
 public String addHomestay(@RequestBody Homestay h) {
  return service.addHomestay(h);
 }
 
 @PutMapping("/homestays")
 public String updateHomestay(@RequestBody Homestay h) {
     return service.updateHomestay(h);
 }

 @PostMapping("/homestays/approve/{id}")
 public String approveHomestay(@PathVariable int id) 
 { 
	 return service.approveHomestay(id);
 }

 @PostMapping("/homestays/reject/{id}")
 public String rejectHomestay(@PathVariable int id) 
 { 
	 return service.rejectHomestay(id);
 }

 @DeleteMapping("/homestays/{id}")
 public String deleteHomestay(@PathVariable int id) 
 { 
	 return service.deleteHomestay(id);
 }

 //ATTRACTIONS
 @GetMapping("/attractions")
 public List<Attraction> attractions() 
 { 
	 return service.getAllAttractions();
 }

 @GetMapping("/attractions/{id}")
 public Attraction getAttractionById(@PathVariable int id) {
  return service.getAllAttractions()
         .stream()
         .filter(a -> a.getId() == id)
         .findFirst()
         .orElse(null);
 }

 @PostMapping("/attractions")
 public String addAttraction(@RequestBody Attraction a) 
 { 
	 return service.addAttraction(a);
 }

 @PutMapping("/attractions")
 public String updateAttraction(@RequestBody Attraction a) 
 { 
	 return service.updateAttraction(a);
 }

 @DeleteMapping("/attractions/{id}")
 public String deleteAttraction(@PathVariable int id) 
 { 
	 return service.deleteAttraction(id);
 }

 //BOOKINGS
 @GetMapping("/bookings")
 public List<Booking> bookings() 
 { 
	 return service.getAllBookings();
 }

 @GetMapping("/bookings/status/{status}")
 public List<Booking> bookingsByStatus(@PathVariable String status) {
  return service.getBookingsByStatus(status);
 }

 @GetMapping("/bookings/payment/{status}")
 public List<Booking> bookingsByPayment(@PathVariable String status) {
  return service.getBookingsByPaymentStatus(status);
 }
}