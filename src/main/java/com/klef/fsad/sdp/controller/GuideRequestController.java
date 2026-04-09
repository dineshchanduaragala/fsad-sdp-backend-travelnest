package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.entity.GuideRequest;
import com.klef.fsad.sdp.service.GuideRequestService;

@RestController
@RequestMapping("guiderequestapi")
@CrossOrigin("*")
public class GuideRequestController 
{
    @Autowired
    private GuideRequestService service;

    // ===================== CREATE REQUEST =====================
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody GuideRequest r)
    {
        String msg = service.createRequest(r);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== VIEW BY GUIDE =====================
    @GetMapping("/guide/{id}")
    public ResponseEntity<List<GuideRequest>> getByGuide(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getGuideRequests(id));
    }

    // ===================== UPDATE STATUS =====================
    @PutMapping("/status/{id}/{status}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable int id,
            @PathVariable String status)
    {
        String msg = service.updateStatus(id, status);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }
}