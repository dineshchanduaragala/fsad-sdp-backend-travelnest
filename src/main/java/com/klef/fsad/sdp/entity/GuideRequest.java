package com.klef.fsad.sdp.entity;

import jakarta.persistence.*;

@Entity
@Table(name="guide_request_table")
public class GuideRequest 
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 private int touristId;
 private int guideId;

 private String requirement;
 private String status; // PENDING / APPROVED / REJECTED

 // GETTERS & SETTERS

 public int getId() { return id; }
 public void setId(int id) { this.id = id; }

 public int getTouristId() { return touristId; }
 public void setTouristId(int touristId) { this.touristId = touristId; }

 public int getGuideId() { return guideId; }
 public void setGuideId(int guideId) { this.guideId = guideId; }

 public String getRequirement() { return requirement; }
 public void setRequirement(String requirement) { this.requirement = requirement; }

 public String getStatus() { return status; }
 public void setStatus(String status) { this.status = status; }
}