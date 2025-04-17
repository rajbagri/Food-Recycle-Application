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
    public FoodService foodService;
    @Autowired
    private DonorService donorService;

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFood() {
        List<FoodItem> foodItems = foodService.getAllFood();
        return new ResponseEntity<>(foodItems, HttpStatus.OK);
    }

    @PostMapping("id/{myId}")
    public ResponseEntity<FoodItem> addFood(@RequestBody FoodItem foodItem, @PathVariable ObjectId myId) {

        foodService.addFood(foodItem, myId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<FoodItem> getFoodById(@PathVariable ObjectId myId) {
        Optional<FoodItem> foodById = foodService.getFoodById(myId);
        if(foodById.isPresent()) {
            return new ResponseEntity<>(foodById.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        foodService.deleteFoodById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
