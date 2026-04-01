package com.klef.fsad.sdp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.klef.fsad.sdp.entity.GuideRequest;

public interface GuideRequestRepository extends JpaRepository<GuideRequest,Integer>
{
 List<GuideRequest> findByGuideId(int guideId);
 List<GuideRequest> findByTouristId(int touristId);
}