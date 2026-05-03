package com.klef.fsad.sdp.service;

import java.util.List;
import java.util.Map;

import com.klef.fsad.sdp.entity.*;

public interface TouristService 
{
    void register(Tourist t);

    Tourist login(String email, String password);

    void updateProfile(Tourist t);

    Tourist getTouristById(int id);

    // ================= HOMESTAYS =================
    List<Homestay> viewHomestays();
    Homestay getHomestayById(int id);

    // ================= ATTRACTIONS =================
    List<Attraction> viewAttractions();

    // ================= GUIDES =================
    List<Guide> viewGuides();
    Guide getGuideById(int id);

    void sendGuideRequest(GuideRequest req);

    // ================= BOOKINGS =================
    void createBooking(Booking b);

    List<Booking> getBookingsByTourist(int touristId);
    void cancelBooking(int bookingId);

    // ================= DASHBOARD =================
    Map<String, Object> getDashboardData(int touristId);
}