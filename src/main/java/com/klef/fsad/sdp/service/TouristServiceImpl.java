package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.repository.*;

@Service
public class TouristServiceImpl implements TouristService
{
 @Autowired 
 private TouristRepository touristRepo;
 @Autowired 
 private HomestayRepository homestayRepo;
 @Autowired 
 private AttractionRepository attractionRepo;
 @Autowired 
 private GuideRepository guideRepo;

 @Override
 public String register(Tourist t) 
 {
  touristRepo.save(t);
  return "Tourist Registered Successfully";
 }

 @Override
 public Tourist login(String email, String password) 
 {
  return touristRepo.findByEmailAndPassword(email,password);
 }

 @Override
 public String updateProfile(Tourist t) 
 {
  touristRepo.save(t);
  return "Profile Updated Successfully";
 }

 @Override
 public List<Homestay> viewHomestays() 
 {
  return homestayRepo.findByApproved(true);
 }

 @Override
 public List<Homestay> searchHomestays(String location) 
 {
  return homestayRepo.findByLocation(location);
 }

 @Override
 public List<Attraction> viewAttractions() 
 {
  return attractionRepo.findAll();
 }

 @Override
 public List<Attraction> searchAttractions(String location) 
 {
  return attractionRepo.findByLocation(location);
 }

 @Override
 public List<Guide> viewGuides() 
 {
  return guideRepo.findByApproved(true);
 }
}
