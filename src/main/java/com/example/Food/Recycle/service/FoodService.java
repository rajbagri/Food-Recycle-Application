package com.example.Food.Recycle.service;


import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.entity.FoodItem;
import com.example.Food.Recycle.repository.FoodRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DonorService donorService;

    public List<FoodItem> getAllFood(){
        return foodRepository.findAll();
    }

    @Transactional
    public void addFood(FoodItem foodItem, ObjectId donorId){

        Optional<Donor> donor = donorService.findDonorById(donorId);
        if(donor.isPresent()){
            foodRepository.save(foodItem);
            donor.get().getFoodItem().add(foodItem);
            donorService.saveDonor(donor.get());
        }

    }


    public void addFood(FoodItem foodItem){
        foodRepository.save(foodItem);
    }

    public Optional<FoodItem> getFoodById(ObjectId id){
        return foodRepository.findById(id);
    }

    public void deleteFoodById(ObjectId id){
        foodRepository.deleteById(id);
    }


}
