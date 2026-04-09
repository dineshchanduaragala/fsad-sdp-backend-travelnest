package com.klef.fsad.sdp.service;

import java.time.LocalDate;
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

    // ===================== CREATE =====================
    @Override
    public String createBooking(Booking b) 
    {
        // Validate dates
        if (b.getCheckIn() == null || b.getCheckOut() == null)
            return "Check-in and Check-out required";

        if (b.getCheckOut().isBefore(b.getCheckIn()))
            return "Invalid Dates";

        if (b.getCheckIn().isBefore(LocalDate.now()))
            return "Check-in cannot be in past";

        b.setPaymentStatus("PENDING");
        b.setBookingStatus("REQUESTED");

        repo.save(b);
        return "Booking Request Sent Successfully";
    }

    // ===================== TOURIST =====================
    @Override
    public List<Booking> getTouristBookings(int touristId) 
    {
        return repo.findByTouristId(touristId);
    }

    // ===================== HOST =====================
    @Override
    public List<Booking> getHostBookings(int hostId) 
    {
        return repo.findByHostId(hostId);
    }

    // ===================== CONFIRM =====================
    @Override
    public String confirmBooking(int id) 
    {
        Booking b = repo.findById(id).orElse(null);

        if (b == null)
            return "Booking Not Found";

        if (!b.getBookingStatus().equals("REQUESTED"))
            return "Only Requested bookings can be confirmed";

        b.setBookingStatus("CONFIRMED");
        repo.save(b);

        return "Booking Confirmed Successfully";
    }

    // ===================== REJECT =====================
    @Override
    public String rejectBooking(int id) 
    {
        Booking b = repo.findById(id).orElse(null);

        if (b == null)
            return "Booking Not Found";

        if (!b.getBookingStatus().equals("REQUESTED"))
            return "Only Requested bookings can be rejected";

        b.setBookingStatus("REJECTED");
        repo.save(b);

        return "Booking Rejected Successfully";
    }

    // ===================== COMPLETE =====================
    @Override
    public String completeBooking(int id) 
    {
        Booking b = repo.findById(id).orElse(null);

        if (b == null)
            return "Booking Not Found";

        if (!b.getBookingStatus().equals("CONFIRMED"))
            return "Only Confirmed bookings can be completed";

        b.setBookingStatus("COMPLETED");
        repo.save(b);

        return "Booking Completed Successfully";
    }

    // ===================== PAYMENT =====================
    @Override
    public String updatePayment(int id, String status) 
    {
        Booking b = repo.findById(id).orElse(null);

        if (b == null)
            return "Booking Not Found";

        if (!b.getBookingStatus().equals("CONFIRMED"))
            return "Payment allowed only after confirmation";

        if (!(status.equals("PAID") || status.equals("PENDING")))
            return "Invalid Payment Status";

        b.setPaymentStatus(status);
        repo.save(b);

        return "Payment Updated Successfully";
    }

    // ===================== ADMIN =====================
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