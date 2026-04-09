package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.entity.Homestay;
import com.klef.fsad.sdp.service.HomestayService;
import com.klef.fsad.sdp.util.FileUploadUtil;

@RestController
@RequestMapping("homestayapi")
@CrossOrigin("*")
public class HomestayController 
{
    @Autowired
    private HomestayService service;

    // ===================== HOST ADD =====================
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> add(
        @RequestParam String name,
        @RequestParam String location,
        @RequestParam String description,
        @RequestParam double price,
        @RequestParam String facilities,
        @RequestParam int hostId,
        @RequestParam(required = false) MultipartFile image,
        @RequestParam(required = false) MultipartFile qr
    ) {
        try 
        {
            String imagePath = (image != null) 
                    ? FileUploadUtil.saveFile(image, "homestays") 
                    : null;

            String qrPath = (qr != null) 
                    ? FileUploadUtil.saveFile(qr, "qr") 
                    : null;

            Homestay h = new Homestay();
            h.setName(name);
            h.setLocation(location);
            h.setDescription(description);
            h.setPrice(price);
            h.setFacilities(facilities);
            h.setHostId(hostId);
            h.setImagePath(imagePath);
            h.setQrPath(qrPath);

            String msg = service.addHomestay(h);

            return ResponseEntity.ok(
                new ApiResponse(msg, "SUCCESS")
            );
        } 
        catch (Exception e) 
        {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Upload Failed", "FAIL"));
        }
    }

    // ===================== ADMIN ADD (AUTO APPROVE) =====================
    @PostMapping("/admin/add")
    public ResponseEntity<ApiResponse> addByAdmin(@RequestBody Homestay h)
    {
        h.setApproved(true);  // ✅ direct approval

        String msg = service.addHomestay(h);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== VIEW ALL =====================
    @GetMapping("/all")
    public ResponseEntity<List<Homestay>> getAll()
    {
        return ResponseEntity.ok(service.getAllHomestays());
    }

    // ===================== VIEW APPROVED =====================
    @GetMapping("/approved")
    public ResponseEntity<List<Homestay>> getApproved()
    {
        return ResponseEntity.ok(service.getApprovedHomestays());
    }

    // ===================== VIEW BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<Homestay> getById(@PathVariable int id)
    {
        return ResponseEntity.ok(service.getById(id));
    }

    // ===================== UPDATE =====================
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody Homestay h)
    {
        String msg = service.updateHomestay(h);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== DELETE =====================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id)
    {
        String msg = service.deleteHomestay(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== APPROVE =====================
    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse> approve(@PathVariable int id)
    {
        String msg = service.approveHomestay(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== REJECT =====================
    @DeleteMapping("/reject/{id}")
    public ResponseEntity<ApiResponse> reject(@PathVariable int id)
    {
        String msg = service.rejectHomestay(id);

        return ResponseEntity.ok(
            new ApiResponse(msg, "SUCCESS")
        );
    }

    // ===================== SEARCH (FILTER ✔) =====================
    @GetMapping("/search/{location}")
    public ResponseEntity<List<Homestay>> search(@PathVariable String location)
    {
        return ResponseEntity.ok(service.searchByLocation(location));
    }

    // ===================== HOST HOMESTAYS =====================
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<Homestay>> getHostHomestays(@PathVariable int hostId)
    {
        return ResponseEntity.ok(service.getHostHomestays(hostId));
    }
}