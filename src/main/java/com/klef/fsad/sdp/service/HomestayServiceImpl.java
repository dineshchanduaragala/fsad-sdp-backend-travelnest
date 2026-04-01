package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Homestay;
import com.klef.fsad.sdp.repository.HomestayRepository;

@Service
public class HomestayServiceImpl implements HomestayService
{
 @Autowired
 private HomestayRepository repo;

 @Override
//HOST
 public String addHomestay(Homestay h) 
 {
     if (!h.isApproved()) {   // ✅ Only set false if not already true
         h.setApproved(false);
     }

     h.setAvailable(true);

     repo.save(h);
     return "Homestay Added";
 }

//ADMIN
public String addHomestayByAdmin(Homestay h) {
  h.setApproved(true);
  repo.save(h);
  return "Added by admin";
}

 @Override
 public List<Homestay> getAllHomestays() 
 {
  return repo.findAll();
 }

 @Override
 public List<Homestay> getApprovedHomestays() 
 {
  return repo.findByApproved(true);
 }

 @Override
 public Homestay getById(int id) 
 {
  return repo.findById(id).orElse(null);
 }

 @Override
 public String updateHomestay(Homestay h) 
 {
  repo.save(h);
  return "Homestay Updated Successfully";
 }

 @Override
 public String deleteHomestay(int id) 
 {
     try {
         repo.deleteById(id);
         return "Deleted";
     } catch (Exception e) {
         return "Cannot delete: Homestay has bookings";
     }
 }

 @Override
 public String approveHomestay(int id) 
 {
  Homestay h = repo.findById(id).orElse(null);

  if(h!=null)
  {
   h.setApproved(true);
   repo.save(h);
   return "Homestay Approved";
  }

  return "Homestay Not Found";
 }

 @Override
 public String rejectHomestay(int id) 
 {
  repo.deleteById(id);
  return "Homestay Rejected & Deleted";
 }

 @Override
 public List<Homestay> searchByLocation(String location) 
 {
  return repo.findByLocation(location);
 }

 @Override
 public List<Homestay> getHostHomestays(int hostId) 
 {
  return repo.findByHostId(hostId);
 }
}
