package com.klef.fsad.sdp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.klef.fsad.sdp.entity.GuideRequest;
import com.klef.fsad.sdp.service.GuideRequestService;

@RestController
@RequestMapping("guiderequestapi")
@CrossOrigin("*")
public class GuideRequestController 
{
 @Autowired
 private GuideRequestService service;

 // CREATE
 @PostMapping("/create")
 public String create(@RequestBody GuideRequest r)
 {
  return service.createRequest(r);
 }

 // VIEW BY GUIDE
 @GetMapping("/guide/{id}")
 public List<GuideRequest> getByGuide(@PathVariable int id)
 {
  return service.getGuideRequests(id);
 }

 // APPROVE / REJECT
 @PutMapping("/status/{id}/{status}")
 public String update(@PathVariable int id,@PathVariable String status)
 {
  return service.updateStatus(id, status);
 }
}