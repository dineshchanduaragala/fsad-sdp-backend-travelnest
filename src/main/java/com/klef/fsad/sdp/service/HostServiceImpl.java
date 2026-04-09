package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Booking;  // ✅ ADDED
import com.klef.fsad.sdp.entity.Host;
import com.klef.fsad.sdp.repository.BookingRepository;  // ✅ ADDED
import com.klef.fsad.sdp.repository.HostRepository;

@Service
public class HostServiceImpl implements HostService
{
    @Autowired
    private HostRepository hostRepo;

    @Autowired
    private BookingRepository bookingRepo;  // ✅ ADDED

    // ===================== REGISTER =====================
    @Override
    public String registerHost(Host h) 
    {
        if (h.getName() == null || h.getName().isEmpty())
            return "Name is required";

        if (h.getEmail() == null || h.getEmail().isEmpty())
            return "Email is required";

        if (hostRepo.findByEmail(h.getEmail()) != null)
            return "Email already exists";

        if (h.getPassword() == null || h.getPassword().isEmpty())
            return "Password is required";

        if (h.getPhone() == null || h.getPhone().isEmpty())
            return "Phone is required";

        if (h.getLocation() == null || h.getLocation().isEmpty())
            return "Location is required";

        // ❌ Host cannot approve himself
        h.setApproved(false);
        h.setAvailable(true);

        hostRepo.save(h);
        return "Host Registered Successfully (Waiting for Approval)";
    }

    // ===================== LOGIN =====================
    @Override
    public Host login(String email, String password) 
    {
        if (email == null || password == null)
            return null;

        // Only approved hosts can login
        return hostRepo.findByEmailAndPasswordAndApproved(email, password, true);
    }

    // ===================== GET ALL =====================
    @Override
    public List<Host> getAllHosts() 
    {
        return hostRepo.findAll();
    }

    // ===================== UPDATE =====================
    @Override
    public String updateHost(Host h) 
    {
        if (h.getId() == 0)
            return "Invalid Host ID";

        Host existing = hostRepo.findById(h.getId()).orElse(null);

        if (existing == null)
            return "Host Not Found";

        // ✅ Keep approval unchanged
        h.setApproved(existing.isApproved());

        hostRepo.save(h);
        return "Host Updated Successfully";
    }

    // ===================== DELETE =====================
    @Override
    public String deleteHost(int id) 
    {
        Host h = hostRepo.findById(id).orElse(null);

        if (h == null)
            return "Host Not Found";

        try {
            hostRepo.deleteById(id);
            return "Host Deleted Successfully";
        } 
        catch (Exception e) {
            return "Cannot delete: Host has active homestays";
        }
    }

    // ✅ NEW OFFLINE BOOKING METHOD (CRITICAL)
    @Override
    public String createOfflineBooking(Booking booking) {
        try {
            if (booking.getHostId() == 0) {
                return "Invalid Host ID";
            }
            
            // Auto-confirm offline booking
            booking.setBookingStatus("CONFIRMED");
            booking.setPaymentStatus("PAID");
            
            bookingRepo.save(booking);
            return "Offline booking created successfully for " + booking.getGuestName();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create offline booking: " + e.getMessage());
        }
    }
}