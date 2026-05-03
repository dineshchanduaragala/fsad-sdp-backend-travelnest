package com.klef.fsad.sdp.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.exception.*;
import com.klef.fsad.sdp.repository.*;

@Service
public class TouristServiceImpl implements TouristService
{
    @Autowired private TouristRepository touristRepo;
    @Autowired private HomestayRepository homestayRepo;
    @Autowired private AttractionRepository attractionRepo;
    @Autowired private GuideRepository guideRepo;
    @Autowired private GuideRequestRepository guideRequestRepo;
    @Autowired private BookingRepository bookingRepo;

    @Override
    public void register(Tourist t)
    {
        if (touristRepo.findByEmail(t.getEmail()) != null)
            throw new RuntimeException("Email already exists");

        touristRepo.save(t);
    }



    @Override
    public void updateProfile(Tourist t)
    {
        Tourist existing = touristRepo.findById(t.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tourist Not Found"));

        existing.setName(t.getName());
        existing.setPhone(t.getPhone());
        existing.setLocation(t.getLocation());

        touristRepo.save(existing);
    }

    @Override
    public Tourist getTouristById(int id)
    {
        return touristRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tourist Not Found"));
    }

    @Override
    public List<Homestay> viewHomestays()
    {
        return homestayRepo.findByApproved(true);
    }

    @Override
    public Homestay getHomestayById(int id)
    {
        return homestayRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Homestay Not Found"));
    }

    @Override
    public List<Attraction> viewAttractions()
    {
        return attractionRepo.findAll();
    }

    @Override
    public List<Guide> viewGuides()
    {
        return guideRepo.findByApproved(true);
    }

    @Override
    public Guide getGuideById(int id)
    {
        return guideRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guide Not Found"));
    }

    @Override
    public void sendGuideRequest(GuideRequest req)
    {
        if (req.getRequirement() == null || req.getRequirement().isEmpty())
            throw new RuntimeException("Requirement is required");

        guideRequestRepo.save(req);
    }

    @Override
    public void createBooking(Booking b)
    {
        if (b.getDays() <= 0 || b.getAmount() <= 0)
            throw new RuntimeException("Invalid booking");

        bookingRepo.save(b);
    }

    @Override
    public List<Booking> getBookingsByTourist(int touristId)
    {
        return bookingRepo.findByTouristId(touristId);
    }

    @Override
    public void cancelBooking(int id)
    {
        Booking b = bookingRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking Not Found"));

        if (!b.getBookingStatus().equals("REQUESTED"))
            throw new RuntimeException("Cannot cancel");

        b.setBookingStatus("REJECTED");
        bookingRepo.save(b);
    }

    @Override
    public Map<String, Object> getDashboardData(int touristId)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("bookings", bookingRepo.findByTouristId(touristId).size());
        map.put("guides", guideRepo.findByApproved(true).size());
        map.put("attractions", attractionRepo.findAll().size());
        map.put("topAttractions", attractionRepo.findAll().stream().limit(4).toList());

        return map;
    }
    
    @Override
    public Tourist login(String email, String password)
    {
        Tourist user = touristRepo.findByEmail(email);

        if (user == null)
            throw new UnauthorizedException("User not found");

        if (user.getPassword() == null || !user.getPassword().equals(password))
            throw new UnauthorizedException("Invalid password");

        return user;
    }
    
}