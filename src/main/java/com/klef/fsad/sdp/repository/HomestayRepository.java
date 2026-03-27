package com.klef.fsad.sdp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Homestay;

@Repository
public interface HomestayRepository extends JpaRepository<Homestay,Integer>
{
 List<Homestay> findByApproved(boolean status);

 List<Homestay> findByLocation(String location);

 List<Homestay> findByHostId(int hostId);
}
