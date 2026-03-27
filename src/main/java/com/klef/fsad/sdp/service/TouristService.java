package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.*;

public interface TouristService 
{
 // AUTH
 String register(Tourist t);
 Tourist login(String email,String password);

 // PROFILE
 String updateProfile(Tourist t);

 // EXPLORE
 List<Homestay> viewHomestays();
 List<Homestay> searchHomestays(String location);

 List<Attraction> viewAttractions();
 List<Attraction> searchAttractions(String location);

 List<Guide> viewGuides();

 // BOOKINGS (will connect later)
}
