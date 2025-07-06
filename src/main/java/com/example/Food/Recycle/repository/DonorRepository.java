package com.example.Food.Recycle.repository;

import com.example.Food.Recycle.entity.Donor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DonorRepository extends MongoRepository<Donor, String> {
    Optional<Donor> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Donor> findByLocation(String location);
    Optional<Donor> findByUserId(String userId);
}
