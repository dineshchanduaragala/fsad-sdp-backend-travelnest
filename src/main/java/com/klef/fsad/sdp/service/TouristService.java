package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.*;

public interface TouristService 
{

 String register(Tourist t);
 Tourist login(String email,String password);
 String updateProfile(Tourist t);
 List<Homestay> viewHomestays();
 List<Homestay> searchHomestays(String location);
 List<Attraction> viewAttractions();
 List<Attraction> searchAttractions(String location);
 List<Guide> viewGuides();
}
