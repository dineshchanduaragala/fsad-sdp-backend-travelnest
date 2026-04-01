package com.klef.fsad.sdp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;   // ✅ IMPORTANT

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

    @PostMapping("/add")
    public String add(
        @RequestParam String name,
        @RequestParam String location,
        @RequestParam String description,
        @RequestParam double price,
        @RequestParam String facilities,
        @RequestParam int hostId,
        @RequestParam(required = false) MultipartFile image,
        @RequestParam(required = false) MultipartFile qr
    ) {
        try {

            String imagePath = (image != null) ? FileUploadUtil.saveFile(image, "homestays") : null;
            String qrPath = (qr != null) ? FileUploadUtil.saveFile(qr, "qr") : null;

            Homestay h = new Homestay();
            h.setName(name);
            h.setLocation(location);
            h.setDescription(description);
            h.setPrice(price);
            h.setFacilities(facilities);
            h.setHostId(hostId);
            h.setImagePath(imagePath);
            h.setQrPath(qrPath);

            return service.addHomestay(h);

        } catch (Exception e) {
            e.printStackTrace();
            return "Upload Failed";
        }
    }
    @PostMapping("/admin/add")
    public String addByAdmin(@RequestBody Homestay h)
    {
        h.setApproved(true);  // ✅ Direct approval
        return service.addHomestay(h);
    }

    // ✅ VIEW ALL (Admin)
    @GetMapping("/all")
    public List<Homestay> getAll()
    {
        return service.getAllHomestays();
    }

    // ✅ VIEW APPROVED (Tourist)
    @GetMapping("/approved")
    public List<Homestay> getApproved()
    {
        return service.getApprovedHomestays();
    }

    // ✅ VIEW BY ID
    @GetMapping("/{id}")
    public Homestay getById(@PathVariable int id)
    {
        return service.getById(id);
    }

    // ⚠️ UPDATE (No image update here – keep simple)
    @PutMapping("/update")
    public String update(@RequestBody Homestay h)
    {
        return service.updateHomestay(h);
    }

    // ✅ DELETE
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id)
    {
        return service.deleteHomestay(id);
    }

    // ✅ APPROVE (Admin)
    @PutMapping("/approve/{id}")
    public String approve(@PathVariable int id)
    {
        return service.approveHomestay(id);
    }

    // ✅ REJECT (Admin)
    @DeleteMapping("/reject/{id}")
    public String reject(@PathVariable int id)
    {
        return service.rejectHomestay(id);
    }

    // ✅ SEARCH (Tourist)
    @GetMapping("/search/{location}")
    public List<Homestay> search(@PathVariable String location)
    {
        return service.searchByLocation(location);
    }

    // ✅ HOST HOMESTAYS
    @GetMapping("/host/{hostId}")
    public List<Homestay> getHostHomestays(@PathVariable int hostId)
    {
        return service.getHostHomestays(hostId);
    }
}