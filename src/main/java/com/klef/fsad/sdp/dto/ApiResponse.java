package com.klef.fsad.sdp.dto;

public class ApiResponse 
{
 private String message;
 private String status;
 private Object data;

 public ApiResponse(String message, String status) {
  this.message = message;
  this.status = status;
 }

 public ApiResponse(String message, String status, Object data) {
  this.message = message;
  this.status = status;
  this.data = data;
 }

 public String getMessage() { return message; }
 public String getStatus() { return status; }
 public Object getData() { return data; }
}