package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.repository.*;

@Service
public class AdminServiceImpl implements AdminService
{
    @Autowired 
    private AdminRepository adminRepo;
    @Autowired 
    private TouristRepository touristRepo;
    @Autowired 
    private HostRepository hostRepo;
    @Autowired 
    private GuideRepository guideRepo;
    @Autowired 
    private HomestayRepository homestayRepo;
    @Autowired 
    private AttractionRepository attractionRepo;
    @Autowired 
    private BookingRepository bookingRepo;

    // ===================== LOGIN =====================
    @Override
    public Admin verifyAdminLogin(String username, String password, String pin) 
    {
        Admin admin = adminRepo.findByUsernameAndPasswordAndPin(username, password, pin);

        if (admin != null && admin.getPin().equals(pin)) 
            return admin;

        return null;
    }

    // ===================== DASHBOARD =====================
    public long getTotalTourists() { return touristRepo.count(); }
    public long getTotalHosts() { return hostRepo.count(); }
    public long getTotalGuides() { return guideRepo.count(); }
    public long getTotalHomestays() { return homestayRepo.count(); }
    public long getTotalAttractions() { return attractionRepo.count(); }
    public long getTotalBookings() { return bookingRepo.count(); }

    // ===================== TOURISTS =====================
    public List<Tourist> viewAllTourists() 
    { 
        return touristRepo.findAll();
    }
    
    @Override
    public String updateTourist(Tourist t)
    {
        Tourist existing = touristRepo.findById(t.getId()).orElse(null);

        if (existing == null)
            return "Tourist Not Found";

        touristRepo.save(t);
        return "Tourist Updated Successfully";
    }

    @Override
    public String deleteTourist(int id)
    {
        touristRepo.deleteById(id);
        return "Tourist Deleted Successfully";
    }

    // ===================== HOSTS =====================
    public List<Host> getAllHosts() 
    { 
        return hostRepo.findAll();
    }

    public List<Host> getPendingHosts() 
    { 
        return hostRepo.findByApproved(false);
    }

    public String approveHost(int id) 
    {
        Host h = hostRepo.findById(id).orElse(null);

        if (h == null) return "Host Not Found";

        h.setApproved(true);
        hostRepo.save(h);
        return "Host Approved Successfully";
    }

    public String rejectHost(int id) 
    {
        if (!hostRepo.existsById(id)) return "Host Not Found";

        hostRepo.deleteById(id);
        return "Host Rejected & Deleted";
    }

    // ===================== GUIDES =====================
    public List<Guide> getAllGuides() 
    { 
        return guideRepo.findAll();
    }

    public List<Guide> getPendingGuides() 
    { 
        return guideRepo.findByApproved(false);
    }

    public String approveGuide(int id) 
    {
        Guide g = guideRepo.findById(id).orElse(null);

        if (g == null) return "Guide Not Found";

        g.setApproved(true);
        guideRepo.save(g);
        return "Guide Approved Successfully";
    }

    public String rejectGuide(int id) 
    {
        if (!guideRepo.existsById(id)) return "Guide Not Found";

        guideRepo.deleteById(id);
        return "Guide Rejected & Deleted";
    }

    // ===================== HOMESTAYS =====================
    public List<Homestay> getAllHomestays() 
    { 
        return homestayRepo.findAll();
    }

    public List<Homestay> getPendingHomestays() 
    { 
        return homestayRepo.findByApproved(false);
    }

    public String approveHomestay(int id)
    {
        Homestay h = homestayRepo.findById(id).orElse(null);

        if (h == null) return "Homestay Not Found";

        h.setApproved(true);
        homestayRepo.save(h);
        return "Homestay Approved Successfully";
    }

    public String rejectHomestay(int id) 
    {
        if (!homestayRepo.existsById(id)) return "Homestay Not Found";

        homestayRepo.deleteById(id);
        return "Homestay Rejected & Deleted";
    }

    public String deleteHomestay(int id) 
    {
        try 
        {
            if (!homestayRepo.existsById(id))
                return "Homestay Not Found";

            homestayRepo.deleteById(id);
            return "Homestay Deleted Successfully";
        } 
        catch (Exception e) 
        {
            return "Cannot Delete: Linked with Bookings";
        }
    }

    public String updateHomestay(Homestay h) 
    {
        if (!homestayRepo.existsById(h.getId()))
            return "Homestay Not Found";

        homestayRepo.save(h);
        return "Homestay Updated Successfully";
    }

    public String addHomestay(Homestay h) 
    {
        h.setApproved(true);  // ✅ Admin adds → auto approved
        h.setAvailable(true);

        homestayRepo.save(h);
        return "Homestay Added Successfully";
    }

    // ===================== ATTRACTIONS =====================
    public List<Attraction> getAllAttractions() 
    { 
        return attractionRepo.findAll();
    }

    public String addAttraction(Attraction a) 
    {
        attractionRepo.save(a);
        return "Attraction Added Successfully";
    }

    public String updateAttraction(Attraction a) 
    {
        if (!attractionRepo.existsById(a.getId()))
            return "Attraction Not Found";

        attractionRepo.save(a);
        return "Attraction Updated Successfully";
    }
    @Autowired
    private TouristRepository touristRepository;

    // ================= TOURISTS =================
    @Override
    public List<Tourist> getAllTourists()
    {
        return touristRepository.findAll();
    }

    public String deleteAttraction(int id) 
    {
        if (!attractionRepo.existsById(id))
            return "Attraction Not Found";

        attractionRepo.deleteById(id);
        return "Attraction Deleted Successfully";
    }

    // ===================== BOOKINGS =====================
    public List<Booking> getAllBookings() 
    { 
        return bookingRepo.findAll();
    }

    public List<Booking> getBookingsByStatus(String status) 
    {
        return bookingRepo.findByBookingStatus(status);
    }

    public List<Booking> getBookingsByPaymentStatus(String paymentStatus) 
    {
        return bookingRepo.findByPaymentStatus(paymentStatus);
    }
    
    @Override
    public String updateHost(Host h)
    {
        Host existing = hostRepo.findById(h.getId()).orElse(null);

        if (existing == null)
            return "Host Not Found";

        // keep approval unchanged
        h.setApproved(existing.isApproved());

        hostRepo.save(h);

        return "Host Updated Successfully";
    }
    
    @Override
    public String deleteHost(int id)
    {
        Host h = hostRepo.findById(id).orElse(null);

        if (h == null)
            return "Host Not Found";

        try {
            hostRepo.deleteById(id);
            return "Host Deleted Successfully";
        } catch (Exception e) {
            return "Cannot delete: Host has linked data";
        }
    }
}