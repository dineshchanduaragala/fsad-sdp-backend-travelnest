package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.Guide;

public interface GuideService
{
    String registerGuide(Guide g);
    Guide login(String email, String password);
    List<Guide> getAllGuides();
    List<Guide> getApprovedGuides();
    String updateGuide(Guide g);
    String deleteGuide(int id);
    Guide getGuideById(int id);
    String toggleAvailability(int id, boolean status);
}