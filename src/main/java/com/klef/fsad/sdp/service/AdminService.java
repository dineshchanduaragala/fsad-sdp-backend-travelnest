package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.*;

public interface AdminService 
{
 // LOGIN
	public Admin verifyAdminLogin(String username, String password, String pin);
 // DASHBOARD
 long getTotalTourists();
 long getTotalHosts();
 long getTotalGuides();
 long getTotalHomestays();
 long getTotalAttractions();
 long getTotalBookings();

 // TOURISTS
 List<Tourist> viewAllTourists();

 // HOSTS
 List<Host> getPendingHosts();
 List<Host> getAllHosts();
 String approveHost(int id);
 String rejectHost(int id);

 // GUIDES
 List<Guide> getPendingGuides();
 List<Guide> getAllGuides();
 String approveGuide(int id);
 String rejectGuide(int id);

 // HOMESTAYS
 List<Homestay> getPendingHomestays();
 List<Homestay> getAllHomestays();
 String approveHomestay(int id);
 String rejectHomestay(int id);
 String deleteHomestay(int id);
 String updateHomestay(Homestay h);
 String addHomestay(Homestay h);

 // ATTRACTIONS
 List<Attraction> getAllAttractions();
 String addAttraction(Attraction a);
 String updateAttraction(Attraction a);
 String deleteAttraction(int id);

 // BOOKINGS
 List<Booking> getAllBookings();
 List<Booking> getBookingsByStatus(String status);
 List<Booking> getBookingsByPaymentStatus(String paymentStatus);
}