package com.klef.fsad.sdp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klef.fsad.sdp.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findByUsername(String username);

    Optional<Admin> findByUsernameAndPin(String username, Integer pin);
    Optional<Admin> findByUsernameAndPasswordAndPin(String username, String password, Integer pin);
}