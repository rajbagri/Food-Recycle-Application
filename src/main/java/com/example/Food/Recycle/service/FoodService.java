package com.example.Food.Recycle.service;

import com.example.Food.Recycle.entity.FoodItem;
import com.example.Food.Recycle.repository.FoodRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public void addFood(FoodItem item) {
        foodRepository.save(item);
    }

    public List<FoodItem> getAllFood() {
        return foodRepository.findAll();
    }

    public Optional<FoodItem> getFoodById(ObjectId id) {
        return foodRepository.findById(id);
    }

    public List<FoodItem> getByDonorId(ObjectId donorId) {
        return foodRepository.findByDonorId(donorId);
    }

    public void deleteFoodById(ObjectId id) {
        foodRepository.deleteById(id);
    }
}
