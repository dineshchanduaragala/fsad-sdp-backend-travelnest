package com.klef.fsad.sdp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klef.fsad.sdp.entity.Guide;
import com.klef.fsad.sdp.repository.GuideRepository;

@Service
public class GuideServiceImpl implements GuideService
{
    @Autowired
    private GuideRepository repo;

    // ===================== REGISTER =====================
    @Override
    public String registerGuide(Guide g) 
    {
        if (g.getEmail() == null || g.getEmail().isEmpty())
            return "Email is required";

        if (repo.findByEmail(g.getEmail()) != null)
            return "Email already exists";

        if (g.getPassword() == null || g.getPassword().isEmpty())
            return "Password is required";

        g.setApproved(false);
        g.setAvailable(true);

        repo.save(g);
        return "Guide Registered Successfully (Waiting for Approval)";
    }

    // ===================== LOGIN =====================
    @Override
    public Guide login(String email, String password) 
    {
        if (email == null || password == null)
            return null;

        return repo.findByEmailAndPasswordAndApproved(email, password, true);
    }

    // ===================== GET ALL =====================
    @Override
    public List<Guide> getAllGuides() 
    {
        return repo.findAll();
    }

    @Override
    public List<Guide> getApprovedGuides() 
    {
        return repo.findByApproved(true);
    }

    // ===================== UPDATE - FIXED VALIDATION ✅
    @Override
    public String updateGuide(Guide g) 
    {
        // ✅ CRITICAL: Check ID first
        if (g.getId() <= 0 || g.getId() == 0) {
            return "Invalid Guide ID: " + g.getId();
        }

        Guide existing = repo.findById(g.getId()).orElse(null);
        if (existing == null) {
            return "Guide Not Found with ID: " + g.getId();
        }

        // ✅ Preserve approval status
        g.setApproved(existing.isApproved());

        repo.save(g);
        return "Guide Updated Successfully";
    }
    
    @Override
    public Guide getGuideById(int id)
    {
        return repo.findById(id).orElse(null);
    }

    // ===================== DELETE =====================
    @Override
    public String deleteGuide(int id) 
    {
        Guide g = repo.findById(id).orElse(null);
        if (g == null)
            return "Guide Not Found";

        repo.deleteById(id);
        return "Guide Deleted Successfully";
    }

    // ===================== AVAILABILITY =====================
    @Override
    public String toggleAvailability(int id, boolean status) 
    {
        Guide g = repo.findById(id).orElse(null);
        if (g == null)
            return "Guide Not Found";

        if (!g.isApproved())
            return "Guide not approved yet";

        g.setAvailable(status);
        repo.save(g);
        return "Availability Updated Successfully";
    }
}