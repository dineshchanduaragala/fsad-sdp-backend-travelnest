package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.Homestay;

public interface HomestayService 
{
 String addHomestay(Homestay h);
 String addHomestayByAdmin(Homestay h);

 List<Homestay> getAllHomestays();

 List<Homestay> getApprovedHomestays();

 Homestay getById(int id);

 String updateHomestay(Homestay h);

 String deleteHomestay(int id);

 String approveHomestay(int id);

 String rejectHomestay(int id);

 List<Homestay> searchByLocation(String location);

 List<Homestay> getHostHomestays(int hostId);
}
