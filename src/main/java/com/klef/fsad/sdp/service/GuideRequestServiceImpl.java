package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.GuideRequest;
import com.klef.fsad.sdp.repository.GuideRequestRepository;

@Service
public class GuideRequestServiceImpl implements GuideRequestService
{
    @Autowired
    private GuideRequestRepository repo;

    // ===================== CREATE =====================
    @Override
    public String createRequest(GuideRequest r) 
    {
        if (r.getGuideId() <= 0 || r.getTouristId() <= 0)
            return "Invalid Guide or Tourist";

        if (r.getRequirement() == null || r.getRequirement().isEmpty())
            return "Requirement cannot be empty";

        r.setStatus("PENDING");

        repo.save(r);
        return "Guide Request Sent Successfully";
    }

    // ===================== GET BY GUIDE =====================
    @Override
    public List<GuideRequest> getGuideRequests(int guideId) 
    {
        return repo.findByGuideId(guideId);
    }

    // ===================== UPDATE STATUS =====================
    @Override
    public String updateStatus(int id, String status) 
    {
        GuideRequest r = repo.findById(id).orElse(null);

        if (r == null)
            return "Request Not Found";

        // ✅ Only allow valid statuses
        if (!(status.equals("APPROVED") || status.equals("REJECTED")))
            return "Invalid Status";

        // ✅ Only PENDING can be updated
        if (!r.getStatus().equals("PENDING"))
            return "Request already processed";

        r.setStatus(status);
        repo.save(r);

        return "Request " + status + " Successfully";
    }
}