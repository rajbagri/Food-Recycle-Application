package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.entity.FoodItem;
import com.example.Food.Recycle.service.DonorService;
import com.example.Food.Recycle.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private DonorService donorService;

    @PostMapping("/add")
    public ResponseEntity<?> addFood(@RequestBody FoodItem foodItem, @RequestParam String userId) {
        Optional<Donor> donorOpt = donorService.findDonorByUserId(userId);
        if (donorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not a registered donor");
        }

        foodItem.setDonorId(donorOpt.get().getId()); // donorId as String
        foodService.addFood(foodItem);
        return new ResponseEntity<>(foodItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFood() {
        return new ResponseEntity<>(foodService.getAllFood(), HttpStatus.OK);
    }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<FoodItem>> getFoodByDonorId(@PathVariable String donorId) {
        return new ResponseEntity<>(foodService.getByDonorId(donorId), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FoodItem> getFoodById(@PathVariable String id) {
        return foodService.getFoodById(id)
                .map(food -> new ResponseEntity<>(food, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteFoodById(@PathVariable String id) {
        foodService.deleteFoodById(id);
        return ResponseEntity.noContent().build();
    }
}
