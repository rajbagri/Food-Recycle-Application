package com.example.Food.Recycle.repository;

import com.example.Food.Recycle.entity.Donor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepository extends MongoRepository<Donor, ObjectId> {

    List<Donor> findByLocation(String location);

}
