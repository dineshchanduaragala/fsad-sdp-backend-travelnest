package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.*;

public interface AdminService 
{
    // ===================== AUTH =====================
    Admin verifyAdminLogin(String username, String password, String pin);

    // ===================== DASHBOARD =====================
    long getTotalTourists();
    long getTotalHosts();
    long getTotalGuides();
    long getTotalHomestays();
    long getTotalAttractions();
    long getTotalBookings();
    
    List<Tourist> getAllTourists();

    // ===================== TOURISTS =====================
    List<Tourist> viewAllTourists();

    // ===================== HOST MANAGEMENT =====================
    List<Host> getAllHosts();
    List<Host> getPendingHosts();
    String approveHost(int id);
    String rejectHost(int id);

    // ===================== GUIDE MANAGEMENT =====================
    List<Guide> getAllGuides();
    List<Guide> getPendingGuides();
    String approveGuide(int id);
    String rejectGuide(int id);

    // ===================== HOMESTAY MANAGEMENT =====================
    List<Homestay> getAllHomestays();
    List<Homestay> getPendingHomestays();
    String addHomestay(Homestay h);        // Admin adds → auto approve
    String updateHomestay(Homestay h);
    String deleteHomestay(int id);
    String approveHomestay(int id);
    String rejectHomestay(int id);

    // ===================== ATTRACTION MANAGEMENT =====================
    List<Attraction> getAllAttractions();
    String addAttraction(Attraction a);
    String updateAttraction(Attraction a);
    String deleteAttraction(int id);

    // ===================== BOOKING MANAGEMENT =====================
    List<Booking> getAllBookings();
    List<Booking> getBookingsByStatus(String status);
    List<Booking> getBookingsByPaymentStatus(String paymentStatus);
}