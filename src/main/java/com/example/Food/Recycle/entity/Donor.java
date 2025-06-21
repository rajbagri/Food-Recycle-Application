package com.example.Food.Recycle.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "donors")
public class Donor {
    @Id
    private ObjectId id;
    private String name;
    private String location;

    private ObjectId userId; // Link to User

    // Getters and Setters
}
