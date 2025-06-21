package com.example.Food.Recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "donors")
@NoArgsConstructor
@AllArgsConstructor
public class Donor {
    @Id
    private ObjectId id;
    private String name;
    private String location;
    private String email;
    private ObjectId userId;
}
