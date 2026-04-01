package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.repository.*;

@Service
public class AdminServiceImpl implements AdminService
{
 @Autowired private AdminRepository adminRepo;
 @Autowired private TouristRepository touristRepo;
 @Autowired private HostRepository hostRepo;
 @Autowired private GuideRepository guideRepo;
 @Autowired private HomestayRepository homestayRepo;
 @Autowired private AttractionRepository attractionRepo;
 @Autowired private BookingRepository bookingRepo;

 // LOGIN
 @Override
 public Admin verifyAdminLogin(String username, String password, String pin) {

     Admin admin = adminRepo.findByUsernameAndPasswordAndPin(username, password, pin);

     if (admin != null && admin.getPin().equals(pin)) {
         return admin;
     }

     return null;
 }

 // DASHBOARD
 public long getTotalTourists() { return touristRepo.count(); }
 public long getTotalHosts() { return hostRepo.count(); }
 public long getTotalGuides() { return guideRepo.count(); }
 public long getTotalHomestays() { return homestayRepo.count(); }
 public long getTotalAttractions() { return attractionRepo.count(); }
 public long getTotalBookings() { return bookingRepo.count(); }

 // TOURISTS
 public List<Tourist> viewAllTourists() { return touristRepo.findAll(); }

 // HOSTS
 public List<Host> getPendingHosts() { return hostRepo.findByApproved(false); }
 public List<Host> getAllHosts() { return hostRepo.findAll(); }

 public String approveHost(int id) {
  Host h = hostRepo.findById(id).orElse(null);
  if(h!=null){ h.setApproved(true); hostRepo.save(h); return "Host Approved"; }
  return "Host Not Found";
 }

 public String rejectHost(int id) {
  hostRepo.deleteById(id);
  return "Host Rejected";
 }

 // GUIDES
 public List<Guide> getPendingGuides() { return guideRepo.findByApproved(false); }
 public List<Guide> getAllGuides() { return guideRepo.findAll(); }

 public String approveGuide(int id) {
  Guide g = guideRepo.findById(id).orElse(null);
  if(g!=null){ g.setApproved(true); guideRepo.save(g); return "Guide Approved"; }
  return "Guide Not Found";
 }

 public String rejectGuide(int id) {
  guideRepo.deleteById(id);
  return "Guide Rejected";
 }

 // HOMESTAYS
 public List<Homestay> getPendingHomestays() { return homestayRepo.findByApproved(false); }
 public List<Homestay> getAllHomestays() { return homestayRepo.findAll(); }

 public String approveHomestay(int id) {
  Homestay h = homestayRepo.findById(id).orElse(null);
  if(h!=null){ h.setApproved(true); homestayRepo.save(h); return "Approved"; }
  return "Not Found";
 }

 public String rejectHomestay(int id) {
  homestayRepo.deleteById(id);
  return "Rejected";
 }

 public String deleteHomestay(int id) {
  homestayRepo.deleteById(id);
  return "Deleted";
 }

 public String updateHomestay(Homestay h) {
     homestayRepo.save(h);
     return "Updated";
 }
 public String addHomestay(Homestay h) {
	    h.setApproved(true);
	    homestayRepo.save(h);
	    return "Added";
	}
 

 // ATTRACTIONS
 public List<Attraction> getAllAttractions() { return attractionRepo.findAll(); }

 public String addAttraction(Attraction a) {
  attractionRepo.save(a);
  return "Added";
 }

 public String updateAttraction(Attraction a) {
  attractionRepo.save(a);
  return "Updated";
 }

 public String deleteAttraction(int id) {
  attractionRepo.deleteById(id);
  return "Deleted";
 }

 // BOOKINGS
 public List<Booking> getAllBookings() { return bookingRepo.findAll(); }

 public List<Booking> getBookingsByStatus(String status) {
	 return bookingRepo.findByBookingStatus(status);
 }

 public List<Booking> getBookingsByPaymentStatus(String paymentStatus) {
  return bookingRepo.findByPaymentStatus(paymentStatus);
 }
}