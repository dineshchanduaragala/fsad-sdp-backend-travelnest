package com.klef.fsad.sdp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="attraction_table")
public class Attraction 
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 @Column(nullable=false)
 private String name;

 @Column(nullable=false)
 private String location;

 @Column(length=500)
 private String description;

 @Column(length=100000) // Base64 Image
 private String image;

 private double entryFee;
 private String timings;

 // GETTERS & SETTERS

 public int getId() { return id; }
 public void setId(int id) { this.id = id; }

 public String getName() { return name; }
 public void setName(String name) { this.name = name; }

 public String getLocation() { return location; }
 public void setLocation(String location) { this.location = location; }

 public String getDescription() { return description; }
 public void setDescription(String description) { this.description = description; }

 public String getImage() { return image; }
 public void setImage(String image) { this.image = image; }

 public double getEntryFee() { return entryFee; }
 public void setEntryFee(double entryFee) { this.entryFee = entryFee; }

 public String getTimings() { return timings; }
 public void setTimings(String timings) { this.timings = timings; }
}
