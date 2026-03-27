package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.Attraction;

public interface AttractionService 
{
 String addAttraction(Attraction a);

 List<Attraction> getAllAttractions();

 Attraction getAttractionById(int id);

 String updateAttraction(Attraction a);

 String deleteAttraction(int id);

 List<Attraction> searchByLocation(String location);
}
