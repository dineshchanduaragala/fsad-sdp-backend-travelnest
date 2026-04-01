package com.klef.fsad.sdp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="homestay_table")
public class Homestay 
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 private String name;
 private String location;

 @Column(length = 500)
 private String description;

 private double price;

 @Column(length = 500)
 private String facilities;

 // ✅ CHANGED
 private String imagePath;
 private String qrPath;

 private boolean available;
 private boolean approved;

 private int hostId;

 // GETTERS & SETTERS

 public int getId() { return id; }
 public void setId(int id) { this.id = id; }

 public String getName() { return name; }
 public void setName(String name) { this.name = name; }

 public String getLocation() { return location; }
 public void setLocation(String location) { this.location = location; }

 public String getDescription() { return description; }
 public void setDescription(String description) { this.description = description; }

 public double getPrice() { return price; }
 public void setPrice(double price) { this.price = price; }

 public String getFacilities() { return facilities; }
 public void setFacilities(String facilities) { this.facilities = facilities; }

 public String getImagePath() { return imagePath; }
 public void setImagePath(String imagePath) { this.imagePath = imagePath; }

 public String getQrPath() { return qrPath; }
 public void setQrPath(String qrPath) { this.qrPath = qrPath; }

 public boolean isAvailable() { return available; }
 public void setAvailable(boolean available) { this.available = available; }

 public boolean isApproved() { return approved; }
 public void setApproved(boolean approved) { this.approved = approved; }

 public int getHostId() { return hostId; }
 public void setHostId(int hostId) { this.hostId = hostId; }
}