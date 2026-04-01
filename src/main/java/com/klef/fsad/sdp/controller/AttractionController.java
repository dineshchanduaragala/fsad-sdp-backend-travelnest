package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.entity.Attraction;
import com.klef.fsad.sdp.service.AttractionService;
import com.klef.fsad.sdp.util.FileUploadUtil;

@RestController
@RequestMapping("attractionapi")
@CrossOrigin("*")
public class AttractionController 
{
 @Autowired
 private AttractionService service;

 // ✅ ADD WITH IMAGE
 @PostMapping("/add")
 public String add(
     @RequestParam String name,
     @RequestParam String location,
     @RequestParam String description,
     @RequestParam double entryFee,
     @RequestParam String timings,
     @RequestParam MultipartFile image
 )
 {
  try 
  {
   String imagePath = FileUploadUtil.saveFile(image, "attractions");

   Attraction a = new Attraction();
   a.setName(name);
   a.setLocation(location);
   a.setDescription(description);
   a.setEntryFee(entryFee);
   a.setTimings(timings);
   a.setImagePath(imagePath);

   return service.addAttraction(a);
  } 
  catch (Exception e) 
  {
   e.printStackTrace();
   return "Upload Failed";
  }
 }

 // VIEW ALL
 @GetMapping("/all")
 public List<Attraction> getAll()
 {
  return service.getAllAttractions();
 }

 // VIEW BY ID
 @GetMapping("/{id}")
 public Attraction getById(@PathVariable int id)
 {
  return service.getAttractionById(id);
 }

 // UPDATE (no image update)
 @PutMapping("/update")
 public String update(@RequestBody Attraction a)
 {
  return service.updateAttraction(a);
 }

 // DELETE
 @DeleteMapping("/delete/{id}")
 public String delete(@PathVariable int id)
 {
  return service.deleteAttraction(id);
 }

 // SEARCH
 @GetMapping("/search/{location}")
 public List<Attraction> search(@PathVariable String location)
 {
  return service.searchByLocation(location);
 }
}