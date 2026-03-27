package com.klef.fsad.sdp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="host_table")
public class Host 
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(nullable=false)
 private String name;

 @Column(nullable=false, unique=true)
 private String email;

 @Column(nullable=false)
 private String password;

 @Column(nullable=false)
 private String phone;

 @Column(nullable=false)
 private String location;

 private boolean approved;
 private boolean available;

 // GETTERS & SETTERS

 public int getId() { return id; }
 public void setId(int id) { this.id = id; }

 public String getName() { return name; }
 public void setName(String name) { this.name = name; }

 public String getEmail() { return email; }
 public void setEmail(String email) { this.email = email; }

 public String getPassword() { return password; }
 public void setPassword(String password) { this.password = password; }

 public String getPhone() { return phone; }
 public void setPhone(String phone) { this.phone = phone; }

 public String getLocation() { return location; }
 public void setLocation(String location) { this.location = location; }

 public boolean isApproved() { return approved; }
 public void setApproved(boolean approved) { this.approved = approved; }

 public boolean isAvailable() { return available; }
 public void setAvailable(boolean available) { this.available = available; }
}
