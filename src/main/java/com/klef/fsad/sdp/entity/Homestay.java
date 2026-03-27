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

 @Column(length = 100000)
 private String image;   // base64

 @Column(length = 100000)
 private String upiQR;   // QR image

 private boolean available;
 private boolean approved;

 // 🔗 Link with Host
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

 public String getImage() { return image; }
 public void setImage(String image) { this.image = image; }

 public String getUpiQR() { return upiQR; }
 public void setUpiQR(String upiQR) { this.upiQR = upiQR; }

 public boolean isAvailable() { return available; }
 public void setAvailable(boolean available) { this.available = available; }

 public boolean isApproved() { return approved; }
 public void setApproved(boolean approved) { this.approved = approved; }

 public int getHostId() { return hostId; }
 public void setHostId(int hostId) { this.hostId = hostId; }
}
