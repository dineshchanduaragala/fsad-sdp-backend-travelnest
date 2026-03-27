package com.klef.fsad.sdp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Host;

@Repository
public interface HostRepository extends JpaRepository<Host,Integer>
{
 Host findByEmailAndPassword(String email,String password);

 List<Host> findByApproved(boolean status);
}
