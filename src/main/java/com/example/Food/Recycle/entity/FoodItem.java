package com.example.Food.Recycle.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "foodItems")
@Data
@NoArgsConstructor
public class FoodItem {

    @Id
    private ObjectId id;

    private String foodName;

    private int quantity;

    private int price;

    private String foodStatus;
}
