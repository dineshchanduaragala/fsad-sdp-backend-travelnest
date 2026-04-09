package com.klef.fsad.sdp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name="booking_table")
public class Booking 
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private int id;

 private int touristId;
 private int homestayId;
 private int hostId;

 private LocalDate checkIn;
 private LocalDate checkOut;

 private double amount;

 private String paymentStatus = "PENDING";
 private String bookingStatus = "REQUESTED";

 private LocalDateTime createdAt = LocalDateTime.now();
 
 private String guestName;      // Offline guest name
 private String phone;          // Offline phone
 private String paymentMode;    // Cash/UPI/Card
 private int days;              // Number of days

 public int getId() {
	return id;
 }

 public void setId(int id) {
	this.id = id;
 }

 public int getTouristId() {
	return touristId;
 }

 public void setTouristId(int touristId) {
	this.touristId = touristId;
 }

 public int getHomestayId() {
	return homestayId;
 }

 public void setHomestayId(int homestayId) {
	this.homestayId = homestayId;
 }

 public int getHostId() {
	return hostId;
 }

 public void setHostId(int hostId) {
	this.hostId = hostId;
 }

 public LocalDate getCheckIn() {
	return checkIn;
 }

 public void setCheckIn(LocalDate checkIn) {
	this.checkIn = checkIn;
 }

 public LocalDate getCheckOut() {
	return checkOut;
 }

 public void setCheckOut(LocalDate checkOut) {
	this.checkOut = checkOut;
 }

 public double getAmount() {
	return amount;
 }

 public void setAmount(double amount) {
	this.amount = amount;
 }

 public String getPaymentStatus() {
	return paymentStatus;
 }

 public void setPaymentStatus(String paymentStatus) {
	this.paymentStatus = paymentStatus;
 }

 public String getBookingStatus() {
	return bookingStatus;
 }

 public void setBookingStatus(String bookingStatus) {
	this.bookingStatus = bookingStatus;
 }

 public LocalDateTime getCreatedAt() {
	return createdAt;
 }

 public void setCreatedAt(LocalDateTime createdAt) {
	this.createdAt = createdAt;
 }

 public String getGuestName() {
	return guestName;
 }

 public void setGuestName(String guestName) {
	this.guestName = guestName;
 }

 public String getPhone() {
	return phone;
 }

 public void setPhone(String phone) {
	this.phone = phone;
 }

 public String getPaymentMode() {
	return paymentMode;
 }

 public void setPaymentMode(String paymentMode) {
	this.paymentMode = paymentMode;
 }

 public int getDays() {
	return days;
 }

 public void setDays(int days) {
	this.days = days;
 }
}