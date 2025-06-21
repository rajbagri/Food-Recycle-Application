package com.example.Food.Recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    private String name;
    private String email;
    private String password;
    private String firebaseUid;

    // Getters and Setters
}

