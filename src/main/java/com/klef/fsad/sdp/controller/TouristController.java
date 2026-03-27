package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
  return service.register(t);
 }

 // LOGIN
 @PostMapping("/login")
 public Tourist login(@RequestBody Tourist t)
 {
  return service.login(t.getEmail(), t.getPassword());
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
