package com.klef.fsad.sdp.service;

import java.util.List;

import com.klef.fsad.sdp.entity.Booking;

public interface BookingService 
{
    // TOURIST
    String createBooking(Booking b);
    List<Booking> getTouristBookings(int touristId);

    // HOST
    List<Booking> getHostBookings(int hostId);
    String confirmBooking(int id);
    String rejectBooking(int id);
    String completeBooking(int id);

    // PAYMENT
    String updatePayment(int id, String status);

    // ADMIN
    List<Booking> getAllBookings();
    List<Booking> getByStatus(String status);
    List<Booking> getByPaymentStatus(String status);
}