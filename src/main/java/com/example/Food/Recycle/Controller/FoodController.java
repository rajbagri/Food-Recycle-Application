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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private DonorService donorService;

    /**
     * ✅ Add food item linked to donorId directly (user doesn't need to pass userId)
     */
    @PostMapping("/add")
    public ResponseEntity<?> addFood(@RequestParam String donorId, @RequestBody FoodItem foodItem) {
        Optional<Donor> donorOpt = donorService.findDonorById(donorId);

        if (donorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No donor found with ID: " + donorId
            );
        }

        // Link food item to donor
        foodItem.setId(new ObjectId().toHexString()); // Generate FoodItem ID
        foodItem.setDonorId(donorId);

        foodService.addFood(foodItem);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "foodId", foodItem.getId(),
                        "message", "Food item added successfully"
                )
        );
    }

    /**
     * ✅ Get all food items
     */
    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFood() {
        return ResponseEntity.ok(foodService.getAllFood());
    }

    /**
     * ✅ Get food by donorId
     */
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<FoodItem>> getFoodByDonorId(@PathVariable String donorId) {
        List<FoodItem> foodList = foodService.getByDonorId(donorId);
        return ResponseEntity.ok(foodList);
    }

    /**
     * ✅ Get food by foodId
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<FoodItem> getFoodById(@PathVariable String id) {
        return foodService.getFoodById(id)
                .map(food -> ResponseEntity.ok(food))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ✅ Delete food by foodId
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteFoodById(@PathVariable String id) {
        Optional<FoodItem> foodOpt = foodService.getFoodById(id);
        if (foodOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No food item found with ID: " + id
            );
        }

        foodService.deleteFoodById(id);
        return ResponseEntity.noContent().build();
    }
}
