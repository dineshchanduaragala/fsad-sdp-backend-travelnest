package com.klef.fsad.sdp.dto;

public class AuthResponse 
{
 private String token;
 private String role;
 private Object user;

 public AuthResponse(String token, String role, Object user) {
  this.token = token;
  this.role = role;
  this.user = user;
 }

 public String getToken() { return token; }
 public String getRole() { return role; }
 public Object getUser() { return user; }
}