package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.Host;
import com.klef.fsad.sdp.service.HostService;

@RestController
@RequestMapping("hostapi")
@CrossOrigin("*")
public class HostController 
{
 @Autowired
 private HostService service;

 // REGISTER
 @PostMapping("/register")
 public String register(@RequestBody Host h)
 {
  return service.registerHost(h);
 }

 // LOGIN
 @PostMapping("/login")
 public Host login(@RequestBody Host h)
 {
  return service.login(h.getEmail(), h.getPassword());
 }

 // VIEW ALL
 @GetMapping("/all")
 public List<Host> getAll()
 {
  return service.getAllHosts();
 }

 // UPDATE
 @PutMapping("/update")
 public String update(@RequestBody Host h)
 {
  return service.updateHost(h);
 }

 // DELETE
 @DeleteMapping("/delete/{id}")
 public String delete(@PathVariable int id)
 {
  return service.deleteHost(id);
 }
}
