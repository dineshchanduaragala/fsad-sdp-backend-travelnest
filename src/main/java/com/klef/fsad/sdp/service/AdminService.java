package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Map;

import com.klef.fsad.sdp.entity.*;

public interface AdminService 
{
	
	Admin validateAdminLogin(String username, String password, Integer pin);
    // ================= DASHBOARD =================
    Map<String, Long> getDashboardStats();

    // ================= TOURISTS =================
    List<Tourist> getAllTourists();
    String updateTourist(Tourist t);
    String deleteTourist(int id);

    // ================= HOSTS =================
    List<Host> getAllHosts();            // ✅ ALL hosts
    List<Host> getPendingHosts();        // ✅ approved = false
    String approveHost(int id);
    String rejectHost(int id);
    String updateHost(Host h);
    String deleteHost(int id);

    // ================= GUIDES =================
    List<Guide> getAllGuides();
    List<Guide> getPendingGuides();
    String approveGuide(int id);
    String rejectGuide(int id);
    String updateGuide(Guide g);
    String deleteGuide(int id);

    // ================= HOMESTAYS =================
    List<Homestay> getAllHomestays();
    List<Homestay> getPendingHomestays();
    String addHomestay(Homestay h);
    String updateHomestay(Homestay h);
    String deleteHomestay(int id);
    String approveHomestay(int id);
    String rejectHomestay(int id);
    Homestay getHomestayById(int id);

    // ================= ATTRACTIONS =================
    List<Attraction> getAllAttractions();
    Attraction getAttractionById(int id);
    String addAttraction(Attraction a);
    String updateAttraction(Attraction a);
    String deleteAttraction(int id);

    // ================= BOOKINGS =================
    List<Booking> getAllBookings();
    String updateBookingStatus(int id, String status);
}