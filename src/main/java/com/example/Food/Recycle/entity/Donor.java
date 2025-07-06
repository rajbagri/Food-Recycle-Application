package com.example.Food.Recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "donors")
@NoArgsConstructor
@AllArgsConstructor
public class Donor {
    @Id
    private String id; // Store _id as String
    private String name;
    private String location;
    private String email;
    private String userId; // userId as String
}
