package com.klef.fsad.sdp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.GuideRequest;

@Repository
public interface GuideRequestRepository extends JpaRepository<GuideRequest,Integer>
{
    List<GuideRequest> findByGuideId(int guideId);
    List<GuideRequest> findByTouristId(int touristId);
}