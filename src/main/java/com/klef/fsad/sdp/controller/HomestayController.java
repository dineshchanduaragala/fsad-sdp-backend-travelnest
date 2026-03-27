package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Homestay;
import com.klef.fsad.sdp.service.HomestayService;

@RestController
@RequestMapping("homestayapi")
@CrossOrigin("*")
public class HomestayController 
{
 @Autowired
 private HomestayService service;

 // ADD (Host)
 @PostMapping("/add")
 public String add(@RequestBody Homestay h)
 {
  return service.addHomestay(h);
 }

 // VIEW ALL (Admin)
 @GetMapping("/all")
 public List<Homestay> getAll()
 {
  return service.getAllHomestays();
 }

 // VIEW APPROVED (Tourist)
 @GetMapping("/approved")
 public List<Homestay> getApproved()
 {
  return service.getApprovedHomestays();
 }

 // VIEW BY ID
 @GetMapping("/{id}")
 public Homestay getById(@PathVariable int id)
 {
  return service.getById(id);
 }

 // UPDATE
 @PutMapping("/update")
 public String update(@RequestBody Homestay h)
 {
  return service.updateHomestay(h);
 }

 // DELETE
 @DeleteMapping("/delete/{id}")
 public String delete(@PathVariable int id)
 {
  return service.deleteHomestay(id);
 }

 // APPROVE (Admin)
 @PutMapping("/approve/{id}")
 public String approve(@PathVariable int id)
 {
  return service.approveHomestay(id);
 }

 // REJECT (Admin)
 @DeleteMapping("/reject/{id}")
 public String reject(@PathVariable int id)
 {
  return service.rejectHomestay(id);
 }

 // SEARCH (Tourist)
 @GetMapping("/search/{location}")
 public List<Homestay> search(@PathVariable String location)
 {
  return service.searchByLocation(location);
 }

 // HOST HOMESTAYS
 @GetMapping("/host/{hostId}")
 public List<Homestay> getHostHomestays(@PathVariable int hostId)
 {
  return service.getHostHomestays(hostId);
 }
}
