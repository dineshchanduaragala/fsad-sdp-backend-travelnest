package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
 public Guide login(@RequestBody Guide g)
 {
  return service.login(g.getEmail(), g.getPassword());
 }

 // VIEW ALL
 @GetMapping("/all")
 public List<Guide> getAll()
 {
  return service.getAllGuides();
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

 // TOGGLE AVAILABILITY
 @PutMapping("/availability/{id}/{status}")
 public String updateAvailability(@PathVariable int id,@PathVariable boolean status)
 {
  return service.toggleAvailability(id, status);
 }
}
