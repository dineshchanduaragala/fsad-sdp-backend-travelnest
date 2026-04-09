package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.*;
import com.klef.fsad.sdp.repository.*;

@Service
public class TouristServiceImpl implements TouristService
{
    @Autowired
    private TouristRepository touristRepo;

    @Autowired
    private HomestayRepository homestayRepo;

    @Autowired
    private AttractionRepository attractionRepo;

    @Autowired
    private GuideRepository guideRepo;

    // ===================== REGISTER =====================
    @Override
    public String register(Tourist t) 
    {
        if (t.getName() == null || t.getName().isEmpty())
            return "Name is required";

        if (t.getEmail() == null || t.getEmail().isEmpty())
            return "Email is required";

        if (touristRepo.findByEmail(t.getEmail()) != null)
            return "Email already exists";

        if (t.getPassword() == null || t.getPassword().isEmpty())
            return "Password is required";

        if (t.getPhone() == null || t.getPhone().isEmpty())
            return "Phone is required";

        touristRepo.save(t);
        return "Tourist Registered Successfully";
    }

    // ===================== LOGIN =====================
    @Override
    public Tourist login(String email, String password) 
    {
        if (email == null || password == null)
            return null;

        return touristRepo.findByEmailAndPassword(email, password);
    }

    // ===================== UPDATE =====================
    @Override
    public String updateProfile(Tourist t) 
    {
        if (t.getId() == 0)
            return "Invalid Tourist ID";

        Tourist existing = touristRepo.findById(t.getId()).orElse(null);

        if (existing == null)
            return "Tourist Not Found";

        touristRepo.save(t);
        return "Profile Updated Successfully";
    }

    // ===================== HOMESTAYS =====================
    @Override
    public List<Homestay> viewHomestays() 
    {
        return homestayRepo.findByApproved(true);
    }

    @Override
    public List<Homestay> searchHomestays(String location) 
    {
        return homestayRepo.findByLocation(location)
                .stream()
                .filter(Homestay::isApproved)
                .toList();
    }

    // ===================== ATTRACTIONS =====================
    @Override
    public List<Attraction> viewAttractions() 
    {
        return attractionRepo.findAll();
    }

    @Override
    public List<Attraction> searchAttractions(String location) 
    {
        return attractionRepo.findByLocation(location);
    }
    @Override
    public Tourist getTouristById(int id)
    {
        return touristRepo.findById(id).orElse(null);
    }

    // ===================== GUIDES =====================
    @Override
    public List<Guide> viewGuides() 
    {
        return guideRepo.findByApproved(true);
    }
}