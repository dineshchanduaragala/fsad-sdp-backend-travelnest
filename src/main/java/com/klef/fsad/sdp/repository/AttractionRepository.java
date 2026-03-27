package com.klef.fsad.sdp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Attraction;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction,Integer>
{
 List<Attraction> findByLocation(String location);
}
