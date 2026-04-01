package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.GuideRequest;

public interface GuideRequestService 
{
 String createRequest(GuideRequest r);

 List<GuideRequest> getGuideRequests(int guideId);

 String updateStatus(int id,String status);
}