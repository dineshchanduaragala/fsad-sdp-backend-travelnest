package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Attraction;
import com.klef.fsad.sdp.service.AttractionService;

@RestController
@RequestMapping("/attractions")
public class AttractionController 
{
 @Autowired
 private AttractionService service;

 // Add Attraction
 @PostMapping("/add")
 public String addAttraction(@RequestBody Attraction a)
 {
  return service.addAttraction(a);
 }

 // Get All Attractions
 @GetMapping("/all")
 public List<Attraction> getAllAttractions()
 {
  return service.getAllAttractions();
 }

 // Get By ID
 @GetMapping("/{id}")
 public Attraction getAttractionById(@PathVariable int id)
 {
  return service.getAttractionById(id);
 }

 // Update
 @PutMapping("/update")
 public String updateAttraction(@RequestBody Attraction a)
 {
  return service.updateAttraction(a);
 }

 // Delete
 @DeleteMapping("/delete/{id}")
 public String deleteAttraction(@PathVariable int id)
 {
  return service.deleteAttraction(id);
 }

 // Search by Location
 @GetMapping("/search/{location}")
 public List<Attraction> searchByLocation(@PathVariable String location)
 {
  return service.searchByLocation(location);
 }
}
