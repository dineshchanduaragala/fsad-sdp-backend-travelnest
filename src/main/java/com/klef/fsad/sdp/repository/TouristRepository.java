package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Tourist;

@Repository
public interface TouristRepository extends JpaRepository<Tourist,Integer>
{
 Tourist findByEmailAndPassword(String email,String password);
}
