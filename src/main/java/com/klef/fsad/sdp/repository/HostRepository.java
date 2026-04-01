package com.klef.fsad.sdp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Host;

@Repository
public interface HostRepository extends JpaRepository<Host,Integer>
{
    // ✅ FIXED (only approved hosts can login)
    Host findByEmailAndPasswordAndApproved(String email,String password, boolean approved);

    List<Host> findByApproved(boolean status);
}