package com.example.Food.Recycle.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "donors")
public class Donor {

    @Id
    private ObjectId id;

    private String donorName;

    private String donorEmail;

    private String password;

    @DBRef
    private List<FoodItem> foodItem = new ArrayList<>();

    private String location;

}
