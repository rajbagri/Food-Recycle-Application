package com.example.Food.Recycle.service;


import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.entity.FoodItem;
import com.example.Food.Recycle.repository.FoodRepository;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.Food.Recycle.repository.DonorRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private FoodRepository foodRepository;

    public void saveDonor(Donor donor) {
//        FoodItem foodItem = donor.getFoodItem();
        donorRepository.save(donor);
    }
    public List<Donor> getAllDonor() {
        return donorRepository.findAll();
    }

    public List<Donor> findDonorByLocation(String Location){
        return donorRepository.findByLocation(Location);
    }

    public Optional<Donor> findDonorById(ObjectId id) {
        return donorRepository.findById(id);
    }



    @Transactional
    public ResponseEntity<?> deleteById(ObjectId id) {
        try{
            donorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("An Error Occured while Deleting Donor");
        }
    }



}
