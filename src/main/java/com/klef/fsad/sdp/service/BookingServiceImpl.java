package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Booking;
import com.klef.fsad.sdp.repository.BookingRepository;

@Service
public class BookingServiceImpl implements BookingService
{
 @Autowired
 private BookingRepository repo;

 @Override
 public String createBooking(Booking b) 
 {
  b.setPaymentStatus("PENDING");
  b.setBookingStatus("REQUESTED");

  repo.save(b);
  return "Booking Request Sent";
 }

 // TOURIST BOOKINGS
 @Override
 public List<Booking> getTouristBookings(int touristId) 
 {
  return repo.findByTouristId(touristId);
 }

 // HOST BOOKINGS
 @Override
 public List<Booking> getHostBookings(int hostId) 
 {
  return repo.findByHostId(hostId);
 }

 // HOST ACTIONS
 @Override
 public String confirmBooking(int id) 
 {
  Booking b = repo.findById(id).orElse(null);

  if(b!=null)
  {
   b.setBookingStatus("CONFIRMED");
   repo.save(b);
   return "Booking Confirmed";
  }

  return "Booking Not Found";
 }

 @Override
 public String rejectBooking(int id) 
 {
  Booking b = repo.findById(id).orElse(null);

  if(b!=null)
  {
   b.setBookingStatus("REJECTED");
   repo.save(b);
   return "Booking Rejected";
  }

  return "Booking Not Found";
 }

 @Override
 public String completeBooking(int id) 
 {
  Booking b = repo.findById(id).orElse(null);

  if(b!=null)
  {
   b.setBookingStatus("COMPLETED");
   repo.save(b);
   return "Booking Completed";
  }

  return "Booking Not Found";
 }

 // PAYMENT UPDATE
 @Override
 public String updatePayment(int id,String status) 
 {
  Booking b = repo.findById(id).orElse(null);

  if(b!=null)
  {
   b.setPaymentStatus(status);
   repo.save(b);
   return "Payment Updated";
  }

  return "Booking Not Found";
 }

 // ADMIN
 @Override
 public List<Booking> getAllBookings() 
 {
  return repo.findAll();
 }

 @Override
 public List<Booking> getByStatus(String status) 
 {
  return repo.findByBookingStatus(status);
 }

 @Override
 public List<Booking> getByPaymentStatus(String status) 
 {
  return repo.findByPaymentStatus(status);
 }
}