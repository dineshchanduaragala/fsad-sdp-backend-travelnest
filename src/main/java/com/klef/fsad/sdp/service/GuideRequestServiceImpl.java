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

 @Override
 public String createRequest(GuideRequest r) 
 {
  r.setStatus("PENDING");
  repo.save(r);
  return "Request Sent";
 }

 @Override
 public List<GuideRequest> getGuideRequests(int guideId) 
 {
  return repo.findByGuideId(guideId);
 }

 @Override
 public String updateStatus(int id,String status) 
 {
  GuideRequest r = repo.findById(id).orElse(null);

  if(r!=null)
  {
   r.setStatus(status);
   repo.save(r);
   return "Updated";
  }

  return "Not Found";
 }
}