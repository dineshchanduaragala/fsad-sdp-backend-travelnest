package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Guide;
import com.klef.fsad.sdp.repository.GuideRepository;

@Service
public class GuideServiceImpl implements GuideService
{
 @Autowired
 private GuideRepository repo;

 @Override
 public String registerGuide(Guide g) 
 {
  g.setApproved(false);   // Admin approval required
  g.setAvailable(true);

  repo.save(g);
  return "Guide Registered Successfully (Waiting for Approval)";
 }

 @Override
 public Guide login(String email, String password) 
 {
  // ✅ Only approved guides can login
  return repo.findByEmailAndPasswordAndApproved(email, password, true);
 }

 @Override
 public List<Guide> getAllGuides() 
 {
  return repo.findAll();
 }

 @Override
 public List<Guide> getApprovedGuides() 
 {
  return repo.findByApproved(true);
 }

 @Override
 public String updateGuide(Guide g) 
 {
  repo.save(g);
  return "Guide Updated Successfully";
 }

 @Override
 public String deleteGuide(int id) 
 {
  repo.deleteById(id);
  return "Guide Deleted Successfully";
 }

 @Override
 public String toggleAvailability(int id, boolean status) 
 {
  Guide g = repo.findById(id).orElse(null);

  if(g!=null)
  {
   g.setAvailable(status);
   repo.save(g);
   return "Availability Updated";
  }

  return "Guide Not Found";
 }
}