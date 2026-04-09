package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.dto.ApiResponse;
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

 // ✅ ADD ATTRACTION WITH IMAGE
 @PostMapping("/add")
 public ApiResponse add(
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

   String msg = service.addAttraction(a);

   return new ApiResponse(msg, "SUCCESS");

  } 
  catch (Exception e) 
  {
   throw new RuntimeException("Image Upload Failed");
  }
 }

 // ✅ GET ALL
 @GetMapping("/all")
 public ApiResponse getAll()
 {
  List<Attraction> list = service.getAllAttractions();
  return new ApiResponse("Attractions Fetched", "SUCCESS", list);
 }

 // ✅ GET BY ID
 @GetMapping("/{id}")
 public ApiResponse getById(@PathVariable int id)
 {
  Attraction a = service.getAttractionById(id);

  if(a == null)
  {
   throw new RuntimeException("Attraction Not Found");
  }

  return new ApiResponse("Attraction Found", "SUCCESS", a);
 }

 // ✅ UPDATE
 @PutMapping("/update")
 public ApiResponse update(@RequestBody Attraction a)
 {
  String msg = service.updateAttraction(a);
  return new ApiResponse(msg, "SUCCESS");
 }

 // ✅ DELETE
 @DeleteMapping("/delete/{id}")
 public ApiResponse delete(@PathVariable int id)
 {
  String msg = service.deleteAttraction(id);
  return new ApiResponse(msg, "SUCCESS");
 }

 // ✅ SEARCH BY LOCATION (FILTER)
 @GetMapping("/search/{location}")
 public ApiResponse search(@PathVariable String location)
 {
  List<Attraction> list = service.searchByLocation(location);

  return new ApiResponse("Search Results", "SUCCESS", list);
 }
}