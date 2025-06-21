package com.example.Food.Recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "fooditems")
@NoArgsConstructor
public class FoodItem {
    @Id
    private ObjectId id;
    private String title;
    private String description;
    private String expiryDate;

    private ObjectId donorId; // Link to Donor

    // Getters and Setters
}
