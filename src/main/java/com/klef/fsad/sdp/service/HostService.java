package com.klef.fsad.sdp.service;

import java.util.List;
import com.klef.fsad.sdp.entity.Booking;  // ✅ ADDED
import com.klef.fsad.sdp.entity.Host;

public interface HostService 
{
    String registerHost(Host h);
    Host login(String email, String password);
    List<Host> getAllHosts();
    String updateHost(Host h);
    String deleteHost(int id);
    
    // ✅ NEW OFFLINE BOOKING
    String createOfflineBooking(Booking b);
}