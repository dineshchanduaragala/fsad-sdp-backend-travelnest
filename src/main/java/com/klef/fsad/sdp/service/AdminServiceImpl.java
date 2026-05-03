package com.klef.fsad.sdp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.repository.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired private TouristRepository touristRepo;
    @Autowired private HostRepository hostRepo;
    @Autowired private GuideRepository guideRepo;
    @Autowired private HomestayRepository homestayRepo;
    @Autowired private AttractionRepository attractionRepo;
    @Autowired private BookingRepository bookingRepo;

    // ================= DASHBOARD =================
    @Override
    public Map<String, Long> getDashboardStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("tourists", touristRepo.count());
        stats.put("hosts", hostRepo.count());
        stats.put("guides", guideRepo.count());
        stats.put("homestays", homestayRepo.count());
        stats.put("attractions", attractionRepo.count());
        stats.put("bookings", bookingRepo.count());

        return stats;
    }

    // ================= TOURISTS =================
    @Override
    public List<Tourist> getAllTourists() {
        return touristRepo.findAll();
    }

    @Override
    public String updateTourist(Tourist t) {
        touristRepo.save(t);
        return "Tourist Updated";
    }

    @Override
    public String deleteTourist(int id) {
        touristRepo.deleteById(id);
        return "Tourist Deleted";
    }

    // ================= HOSTS =================
    @Override
    public List<Host> getAllHosts() {
        return hostRepo.findAll(); // 🔥 IMPORTANT FIX
    }

    @Override
    public List<Host> getPendingHosts() {
        return hostRepo.findByApproved(false);
    }

    @Override
    public String approveHost(int id) {
        Host h = hostRepo.findById(id).orElse(null);
        if (h == null) return "Host not found";

        h.setApproved(true);
        hostRepo.save(h);
        return "Host Approved";
    }

    @Override
    public String rejectHost(int id) {
        hostRepo.deleteById(id);
        return "Host Rejected & Deleted";
    }

    @Override
    public String updateHost(Host h) {

        Host existing = hostRepo.findById(h.getId()).orElse(null);

        if (existing == null) {
            return "Host not found";
        }

        existing.setName(h.getName());
        existing.setLocation(h.getLocation());

        // KEEP OLD VALUES (VERY IMPORTANT)
        existing.setEmail(existing.getEmail());
        existing.setPassword(existing.getPassword());
        existing.setPhone(existing.getPhone());
        existing.setApproved(existing.isApproved());
        existing.setAvailable(existing.isAvailable());
        existing.setRating(existing.getRating());

        hostRepo.save(existing);

        return "Host Updated Successfully";
    }

    @Override
    public String deleteHost(int id) {
        hostRepo.deleteById(id);
        return "Host Deleted";
    }

    // ================= GUIDES =================
    @Override
    public List<Guide> getAllGuides() {
        return guideRepo.findAll();
    }

    @Override
    public List<Guide> getPendingGuides() {
        return guideRepo.findByApproved(false);
    }

    @Override
    public String approveGuide(int id) {
        Guide g = guideRepo.findById(id).orElse(null);
        if (g == null) return "Guide not found";

        g.setApproved(true);
        guideRepo.save(g);
        return "Guide Approved";
    }

    @Override
    public String rejectGuide(int id) {
        guideRepo.deleteById(id);
        return "Guide Rejected";
    }

    @Override
    public String updateGuide(Guide g) {

        Guide existing = guideRepo.findById(g.getId()).orElse(null);

        if (existing == null) {
            return "Guide not found";
        }

        existing.setName(g.getName());
        existing.setLocation(g.getLocation());
        existing.setPhone(g.getPhone());
        existing.setLanguages(g.getLanguages());
        existing.setExperience(g.getExperience());

        guideRepo.save(existing);

        return "Guide Updated Successfully";
    }

    @Override
    public String deleteGuide(int id) {
        guideRepo.deleteById(id);
        return "Guide Deleted";
    }

    // ================= HOMESTAYS =================
    @Override
    public List<Homestay> getAllHomestays() {
        return homestayRepo.findAll();
    }

    @Override
    public List<Homestay> getPendingHomestays() {
        return homestayRepo.findByApproved(false);
    }

    @Override
    public String addHomestay(Homestay h) {
        homestayRepo.save(h);
        return "Homestay Added";
    }

    @Override
    public String updateHomestay(Homestay h) {
        homestayRepo.save(h);
        return "Homestay Updated";
    }

    @Override
    public String deleteHomestay(int id) {
        homestayRepo.deleteById(id);
        return "Homestay Deleted";
    }

    @Override
    public String approveHomestay(int id) {
        Homestay h = homestayRepo.findById(id).orElse(null);
        if (h == null) return "Not found";

        h.setApproved(true);
        homestayRepo.save(h);
        return "Approved";
    }

    @Override
    public String rejectHomestay(int id) {
        homestayRepo.deleteById(id);
        return "Rejected";
    }

    // ================= ATTRACTIONS =================
    @Override
    public List<Attraction> getAllAttractions() {
        return attractionRepo.findAll();
    }

    @Override
    public Attraction getAttractionById(int id) {
        return attractionRepo.findById(id).orElse(null);
    }

    @Override
    public String addAttraction(Attraction a) {
        attractionRepo.save(a);
        return "Attraction Added";
    }

    @Override
    public String updateAttraction(Attraction a) {
        attractionRepo.save(a);
        return "Attraction Updated";
    }

    @Override
    public String deleteAttraction(int id) {
        attractionRepo.deleteById(id);
        return "Attraction Deleted";
    }

    // ================= BOOKINGS =================
    @Override
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    @Override
    public String updateBookingStatus(int id, String status) {
        Booking b = bookingRepo.findById(id).orElse(null);
        if (b == null) return "Booking not found";

        b.setBookingStatus(status);
        bookingRepo.save(b);

        return "Booking Updated";
    }
    
    @Autowired
    private AdminRepository adminRepo;

    @Override
    public Admin validateAdminLogin(String username, String password, Integer pin) {
        Optional<Admin> adminOpt = adminRepo.findByUsername(username);

        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();

            if (admin.getPassword().equals(password) && admin.getPin().equals(pin)) {
                return admin;
            }
        }
        return null;
    }
    
    @Override
    public Homestay getHomestayById(int id) {
        return homestayRepo.findById(id).orElse(null);
    }
}