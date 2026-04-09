package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Homestay;
import com.klef.fsad.sdp.repository.HomestayRepository;

@Service
public class HomestayServiceImpl implements HomestayService
{
    @Autowired
    private HomestayRepository repo;

    // ===================== HOST ADD =====================
    @Override
    public String addHomestay(Homestay h) 
    {
        if (h.getName() == null || h.getName().isEmpty())
            return "Homestay name required";

        if (h.getLocation() == null || h.getLocation().isEmpty())
            return "Location required";

        if (h.getPrice() <= 0)
            return "Invalid price";

        if (h.getHostId() <= 0)
            return "Invalid Host";

        // ❌ HOST cannot approve
        h.setApproved(false);
        h.setAvailable(true);

        repo.save(h);
        return "Homestay Added Successfully (Waiting for Approval)";
    }

    // ===================== ADMIN ADD =====================
    @Override
    public String addHomestayByAdmin(Homestay h) 
    {
        if (h.getName() == null || h.getName().isEmpty())
            return "Homestay name required";

        if (h.getLocation() == null || h.getLocation().isEmpty())
            return "Location required";

        if (h.getPrice() <= 0)
            return "Invalid price";

        h.setApproved(true);   // ✅ Direct approval
        h.setAvailable(true);

        repo.save(h);
        return "Homestay Added & Approved Successfully";
    }

    // ===================== GET =====================
    @Override
    public List<Homestay> getAllHomestays() 
    {
        return repo.findAll();
    }

    @Override
    public List<Homestay> getApprovedHomestays() 
    {
        return repo.findByApproved(true);
    }

    @Override
    public Homestay getById(int id) 
    {
        return repo.findById(id).orElse(null);
    }

    // ===================== UPDATE =====================
    @Override
    public String updateHomestay(Homestay h) 
    {
        if (h.getId() == 0)
            return "Invalid Homestay ID";

        Homestay existing = repo.findById(h.getId()).orElse(null);

        if (existing == null)
            return "Homestay Not Found";

        // ✅ Keep approval unchanged
        h.setApproved(existing.isApproved());
        h.setHostId(existing.getHostId());

        repo.save(h);
        return "Homestay Updated Successfully";
    }

    // ===================== DELETE =====================
    @Override
    public String deleteHomestay(int id) 
    {
        Homestay h = repo.findById(id).orElse(null);

        if (h == null)
            return "Homestay Not Found";

        try {
            repo.deleteById(id);
            return "Homestay Deleted Successfully";
        } 
        catch (Exception e) {
            return "Cannot delete: Active bookings exist";
        }
    }

    // ===================== APPROVE =====================
    @Override
    public String approveHomestay(int id) 
    {
        Homestay h = repo.findById(id).orElse(null);

        if (h == null)
            return "Homestay Not Found";

        if (h.isApproved())
            return "Already Approved";

        h.setApproved(true);
        repo.save(h);

        return "Homestay Approved Successfully";
    }

    // ===================== REJECT =====================
    @Override
    public String rejectHomestay(int id) 
    {
        Homestay h = repo.findById(id).orElse(null);

        if (h == null)
            return "Homestay Not Found";

        repo.deleteById(id);
        return "Homestay Rejected & Deleted";
    }

    // ===================== SEARCH =====================
    @Override
    public List<Homestay> searchByLocation(String location) 
    {
        return repo.findByLocation(location);
    }

    // ===================== HOST =====================
    @Override
    public List<Homestay> getHostHomestays(int hostId) 
    {
        return repo.findByHostId(hostId);
    }
}