package com.klef.fsad.sdp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Guide;

@Repository
public interface GuideRepository extends JpaRepository<Guide,Integer>
{
 Guide findByEmailAndPassword(String email,String password);

 List<Guide> findByApproved(boolean status);
}
