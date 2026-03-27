package com.klef.fsad.sdp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer>
{
 List<Booking> findByTouristId(int touristId);
 List<Booking> findByHostId(int hostId);

 List<Booking> findByBookingStatus(String status);
 List<Booking> findByPaymentStatus(String status);
}