package com.klef.fsad.sdp.entity;

import java.time.LocalDate;

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

 private String paymentStatus;   // PENDING / PAID
 private String bookingStatus;   // REQUESTED / CONFIRMED / REJECTED / COMPLETED
 
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
}