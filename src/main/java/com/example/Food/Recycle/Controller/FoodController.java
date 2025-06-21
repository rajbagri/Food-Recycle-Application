package com.example.Food.Recycle.Controller;


import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.entity.FoodItem;
import com.example.Food.Recycle.service.DonorService;
import com.example.Food.Recycle.service.FoodService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private DonorService donorService;

    // Only DONOR (linked to user) can add food
    @PostMapping("/add")
    public ResponseEntity<?> addFood(@RequestBody FoodItem foodItem, @RequestParam ObjectId userId) {
        Optional<Donor> donorOpt = donorService.findDonorByUserId(userId);
        if (donorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not a donor");
        }

        foodItem.setDonorId(donorOpt.get().getId());
        foodService.addFood(foodItem);
        return new ResponseEntity<>(foodItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFood() {
        return new ResponseEntity<>(foodService.getAllFood(), HttpStatus.OK);
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<FoodItem>> getFoodByDonorId(@PathVariable ObjectId donorId) {
        return new ResponseEntity<>(foodService.getByDonorId(donorId), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FoodItem> getFoodById(@PathVariable ObjectId id) {
        Optional<FoodItem> food = foodService.getFoodById(id);
        return food.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.noContent().build();
    }
}
