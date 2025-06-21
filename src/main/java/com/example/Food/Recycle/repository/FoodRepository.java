package com.example.Food.Recycle.repository;

import com.example.Food.Recycle.entity.FoodItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends MongoRepository<FoodItem, ObjectId> {
    List<FoodItem> findByDonorId(ObjectId donorId);
}
