package com.example.Food.Recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "fooditems")
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {
    @Id
    private String id; // Store _id as String
    private String title;
    private String description;
    private String expiryDate;
    private String donorId; // Link to Donor as String
}
