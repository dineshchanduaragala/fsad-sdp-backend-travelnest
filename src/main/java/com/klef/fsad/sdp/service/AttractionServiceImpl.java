package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Attraction;
import com.klef.fsad.sdp.repository.AttractionRepository;

@Service
public class AttractionServiceImpl implements AttractionService
{
 @Autowired
 private AttractionRepository repo;

 @Override
 public String addAttraction(Attraction a) 
 {
  repo.save(a);
  return "Attraction Added Successfully";
 }

 @Override
 public List<Attraction> getAllAttractions() 
 {
  return repo.findAll();
 }

 @Override
 public Attraction getAttractionById(int id) 
 {
  return repo.findById(id).orElse(null);
 }

 @Override
 public String updateAttraction(Attraction a) 
 {
  repo.save(a);
  return "Attraction Updated Successfully";
 }

 @Override
 public String deleteAttraction(int id) 
 {
  repo.deleteById(id);
  return "Attraction Deleted Successfully";
 }

 @Override
 public List<Attraction> searchByLocation(String location) 
 {
  return repo.findByLocation(location);
 }
}
