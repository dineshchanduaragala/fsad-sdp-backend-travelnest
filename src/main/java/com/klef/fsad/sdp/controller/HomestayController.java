package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.klef.fsad.sdp.dto.ApiResponse;
import com.klef.fsad.sdp.entity.Homestay;
import com.klef.fsad.sdp.service.HomestayService;

@RestController
@RequestMapping("homestayapi")
@CrossOrigin("*")
public class HomestayController 
{
    @Autowired
    private HomestayService service;

    // ===================== ADD =====================
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
            Homestay h = new Homestay();
            h.setName(name);
            h.setLocation(location);
            h.setDescription(description);
            h.setPrice(price);
            h.setFacilities(facilities);
            h.setHostId(hostId);

            if (image != null && !image.isEmpty()) {
                h.setImage(image.getBytes());
                h.setImageType(image.getContentType());
            }

            if (qr != null && !qr.isEmpty()) {
                h.setQrImage(qr.getBytes());
                h.setQrImageType(qr.getContentType());
            }

            String msg = service.addHomestay(h);

            return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(new ApiResponse("Upload Failed ❌", "FAIL"));
        }
    }

    // ===================== ADMIN ADD =====================
    @PostMapping("/admin/add")
    public ResponseEntity<ApiResponse> addByAdmin(@RequestBody Homestay h)
    {
        h.setApproved(true);
        String msg = service.addHomestay(h);

        return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));
    }

    // ===================== GET ALL =====================
    @GetMapping("/all")
    public ResponseEntity<List<Homestay>> getAll()
    {
        return ResponseEntity.ok(service.getAllHomestays());
    }

    // ===================== GET APPROVED =====================
    @GetMapping("/approved")
    public ResponseEntity<List<Homestay>> getApproved()
    {
        return ResponseEntity.ok(service.getApprovedHomestays());
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id)
    {
        Homestay h = service.getById(id);

        if (h == null) {
            return ResponseEntity.status(404)
                .body(new ApiResponse("Homestay Not Found ❌", "FAIL"));
        }

        return ResponseEntity.ok(h);
    }

    // ===================== UPDATE (🔥 FIXED) =====================
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(
        @RequestParam int id,
        @RequestParam String name,
        @RequestParam String location,
        @RequestParam String description,
        @RequestParam double price,
        @RequestParam String facilities,
        @RequestParam boolean available,
        @RequestParam(required = false) MultipartFile image,
        @RequestParam(required = false) MultipartFile qr
    ) {
        try 
        {
            Homestay h = service.getById(id);

            if (h == null) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse("Homestay Not Found ❌", "FAIL"));
            }

            h.setName(name);
            h.setLocation(location);
            h.setDescription(description);
            h.setPrice(price);
            h.setFacilities(facilities);
            h.setAvailable(available);

            if (image != null && !image.isEmpty()) {
                h.setImage(image.getBytes());
                h.setImageType(image.getContentType());
            }

            if (qr != null && !qr.isEmpty()) {
                h.setQrImage(qr.getBytes());
                h.setQrImageType(qr.getContentType());
            }

            String msg = service.updateHomestay(h);

            return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(new ApiResponse("Update Failed ❌", "FAIL"));
        }
    }

    // ===================== DELETE =====================
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id)
    {
        String msg = service.deleteHomestay(id);
        return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));
    }

    // ===================== APPROVE =====================
    @PutMapping("/approve/{id}")
    public ResponseEntity<ApiResponse> approve(@PathVariable int id)
    {
        String msg = service.approveHomestay(id);
        return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));
    }

    // ===================== REJECT =====================
    @DeleteMapping("/reject/{id}")
    public ResponseEntity<ApiResponse> reject(@PathVariable int id)
    {
        String msg = service.rejectHomestay(id);
        return ResponseEntity.ok(new ApiResponse(msg, "SUCCESS"));
    }

    // ===================== SEARCH =====================
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

    // ===================== IMAGE =====================
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {

        Homestay h = service.getById(id);

        if (h == null || h.getImage() == null) {
            return ResponseEntity.notFound().build();
        }

        String type = h.getImageType();

        if (type == null || type.isEmpty()) {
            type = "image/jpeg";
        }

        return ResponseEntity.ok()
            .header("Access-Control-Allow-Origin", "*")
            .contentType(MediaType.parseMediaType(type))
            .body(h.getImage());
    }

    // ===================== QR =====================
    @GetMapping("/qr/{id}")
    public ResponseEntity<byte[]> getQR(@PathVariable int id) {
        Homestay h = service.getById(id);

        if (h == null || h.getQrImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
            .contentType(MediaType.valueOf(h.getQrImageType()))
            .body(h.getQrImage());
    }
}