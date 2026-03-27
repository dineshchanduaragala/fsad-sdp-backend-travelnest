package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Attraction;
import com.klef.fsad.sdp.service.AttractionService;

@RestController
@RequestMapping("attractionapi")
@CrossOrigin("*")
public class AttractionController 
{
 @Autowired
 private AttractionService service;

 @PostMapping("/add")
 public String add(@RequestBody Attraction a)
 {
  return service.addAttraction(a);
 }

 @GetMapping("/all")
 public List<Attraction> getAll()
 {
  return service.getAllAttractions();
 }

 @GetMapping("/{id}")
 public Attraction getById(@PathVariable int id)
 {
  return service.getAttractionById(id);
 }

 @PutMapping("/update")
 public String update(@RequestBody Attraction a)
 {
  return service.updateAttraction(a);
 }

 @DeleteMapping("/delete/{id}")
 public String delete(@PathVariable int id)
 {
  return service.deleteAttraction(id);
 }

 @GetMapping("/search/{location}")
 public List<Attraction> search(@PathVariable String location)
 {
  return service.searchByLocation(location);
 }
}
