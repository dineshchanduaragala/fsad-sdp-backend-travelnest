package com.klef.fsad.sdp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.klef.fsad.sdp.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String>
{
    // ✅ UPDATED METHOD WITH PIN
    Admin findByUsernameAndPasswordAndPin(String username, String password, String pin);
}