package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Host;
import com.klef.fsad.sdp.repository.HostRepository;

@Service
public class HostServiceImpl implements HostService
{
 @Autowired
 private HostRepository repo;

 @Override
 public String registerHost(Host h) 
 {
  h.setApproved(false); // Admin approval needed
  h.setAvailable(true);

  repo.save(h);
  return "Host Registered Successfully (Waiting for Approval)";
 }

 @Override
 public Host login(String email, String password) 
 {
  return repo.findByEmailAndPassword(email,password);
 }

 @Override
 public List<Host> getAllHosts() 
 {
  return repo.findAll();
 }

 @Override
 public String updateHost(Host h) 
 {
  repo.save(h);
  return "Host Updated Successfully";
 }

 @Override
 public String deleteHost(int id) 
 {
  repo.deleteById(id);
  return "Host Deleted Successfully";
 }
}
