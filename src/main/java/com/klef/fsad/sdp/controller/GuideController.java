package com.klef.fsad.sdp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.klef.fsad.sdp.entity.Guide;
import com.klef.fsad.sdp.service.GuideService;

@RestController
@RequestMapping("guideapi")
@CrossOrigin("*")
public class GuideController 
{
 @Autowired
 private GuideService service;

 // REGISTER
 @PostMapping("/register")
 public String register(@RequestBody Guide g)
 {
  return service.registerGuide(g);
 }

 // LOGIN
 @PostMapping("/login")
 public ResponseEntity<?> login(@RequestBody Guide g)
 {
     Guide user = service.login(g.getEmail(), g.getPassword());

     if(user != null)
     {
         return ResponseEntity.ok(user);
     }
     else
     {
         return ResponseEntity.status(401).body("Invalid Credentials or Not Approved");
     }
 }

 // VIEW ALL APPROVED GUIDES (IMPORTANT FIX)
 @GetMapping("/all")
 public List<Guide> getAll()
 {
  return service.getApprovedGuides();
 }

 // UPDATE PROFILE
 @PutMapping("/update")
 public String update(@RequestBody Guide g)
 {
  return service.updateGuide(g);
 }

 // DELETE
 @DeleteMapping("/delete/{id}")
 public String delete(@PathVariable int id)
 {
  return service.deleteGuide(id);
 }

 // AVAILABILITY
 @PutMapping("/availability/{id}/{status}")
 public String updateAvailability(@PathVariable int id,@PathVariable boolean status)
 {
  return service.toggleAvailability(id, status);
 }
}