package com.klef.fsad.sdp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klef.fsad.sdp.entity.Attraction;
import com.klef.fsad.sdp.repository.AttractionRepository;

@Service
public class AttractionServiceImpl implements AttractionService
{
    @Autowired
    private AttractionRepository repo;

    // ===================== ADD =====================
    @Override
    public String addAttraction(Attraction a) 
    {
        repo.save(a);
        return "Attraction Added Successfully";
    }

    // ===================== GET ALL =====================
    @Override
    public List<Attraction> getAllAttractions() 
    {
        return repo.findAll();
    }

    // ===================== GET BY ID =====================
    @Override
    public Attraction getAttractionById(int id) 
    {
        return repo.findById(id).orElse(null);
    }

    // ===================== UPDATE =====================
    @Override
    public String updateAttraction(Attraction a) 
    {
        if (!repo.existsById(a.getId()))
            return "Attraction Not Found";

        repo.save(a);
        return "Attraction Updated Successfully";
    }

    // ===================== DELETE =====================
    @Override
    public String deleteAttraction(int id) 
    {
        if (!repo.existsById(id))
            return "Attraction Not Found";

        repo.deleteById(id);
        return "Attraction Deleted Successfully";
    }

    // ===================== SEARCH (FILTER ✔) =====================
    @Override
    public List<Attraction> searchByLocation(String location) 
    {
        return repo.findByLocation(location);
    }
}