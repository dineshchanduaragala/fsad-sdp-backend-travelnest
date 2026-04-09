package com.klef.fsad.sdp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler 
{
 @ExceptionHandler(ResourceNotFoundException.class)
 public ResponseEntity<ApiResponse> handleNotFound(ResourceNotFoundException ex)
 {
  return ResponseEntity.status(404)
      .body(new ApiResponse(ex.getMessage(), "FAILED"));
 }

 @ExceptionHandler(UnauthorizedException.class)
 public ResponseEntity<ApiResponse> handleUnauthorized(UnauthorizedException ex)
 {
  return ResponseEntity.status(401)
      .body(new ApiResponse(ex.getMessage(), "FAILED"));
 }

 @ExceptionHandler(Exception.class)
 public ResponseEntity<ApiResponse> handleGeneral(Exception ex)
 {
  return ResponseEntity.status(500)
      .body(new ApiResponse("Something went wrong", "ERROR"));
 }
}