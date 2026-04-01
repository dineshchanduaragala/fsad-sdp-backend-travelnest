package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.Guide;

public interface GuideService 
{
 String registerGuide(Guide g);

 Guide login(String email,String password);

 List<Guide> getAllGuides();

 String updateGuide(Guide g);

 String deleteGuide(int id);

 String toggleAvailability(int id, boolean status);
 List<Guide> getApprovedGuides();
}
