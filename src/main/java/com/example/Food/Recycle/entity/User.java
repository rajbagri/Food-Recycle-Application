package com.example.Food.Recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id; // Store _id as String
    private String name;
    private String email;
    private String password;
    private String firebaseUid;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
