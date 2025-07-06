package com.example.Food.Recycle.service;

import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.repository.DonorRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public Donor saveDonor(Donor donor) {
        donorRepository.save(donor);
        return donor;
    }

    public List<Donor> getAllDonor() {
        return donorRepository.findAll();
    }

    public Optional<Donor> findByEmail(String email) {
        return donorRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return donorRepository.existsByEmail(email);
    }

    public List<Donor> findDonorByLocation(String location) {
        return donorRepository.findByLocation(location);
    }

    public Optional<Donor> findDonorById(ObjectId id) {
        return donorRepository.findById(id);
    }

    public Optional<Donor> findDonorByUserId(String userId) {
        return donorRepository.findByUserId(userId);
    }

    public void deleteById(ObjectId id) {
        donorRepository.deleteById(id);
    }
}
