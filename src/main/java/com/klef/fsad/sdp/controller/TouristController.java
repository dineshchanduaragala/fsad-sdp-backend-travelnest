package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.service.TouristService;

@RestController
@RequestMapping("touristapi")
@CrossOrigin("*")
public class TouristController 
{
 @Autowired
 private TouristService service;

 // REGISTER
 @PostMapping("/register")
 public String register(@RequestBody Tourist t)
 {
	 service.register(t);
	 return "Tourist Registered Successfully";
 }

 @PostMapping("/login")
 public ResponseEntity<?> login(@RequestBody Tourist t)
 {
     Tourist user = service.login(t.getEmail(), t.getPassword());

     if(user != null)
     {
         return ResponseEntity.ok(user);
     }
     else
     {
         return ResponseEntity.status(401).body("Invalid Credentials");
     }
 }
 // UPDATE PROFILE
 @PutMapping("/update")
 public String update(@RequestBody Tourist t)
 {
  return service.updateProfile(t);
 }

 // VIEW HOMESTAYS
 @GetMapping("/homestays")
 public List<Homestay> homestays()
 {
  return service.viewHomestays();
 }

 // SEARCH HOMESTAYS
 @GetMapping("/homestays/search/{location}")
 public List<Homestay> searchHomestays(@PathVariable String location)
 {
  return service.searchHomestays(location);
 }

 // VIEW ATTRACTIONS
 @GetMapping("/attractions")
 public List<Attraction> attractions()
 {
  return service.viewAttractions();
 }

 // SEARCH ATTRACTIONS
 @GetMapping("/attractions/search/{location}")
 public List<Attraction> searchAttractions(@PathVariable String location)
 {
  return service.searchAttractions(location);
 }

 // VIEW GUIDES
 @GetMapping("/guides")
 public List<Guide> guides()
 {
  return service.viewGuides();
 }
}
