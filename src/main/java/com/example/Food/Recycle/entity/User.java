package com.example.Food.Recycle.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {

    @Id
    private ObjectId id;

    private String firebaseUid;

    @NonNull
    private String name;

    @NonNull
    private String email;

    private String password;
    private String phone;
    private String address;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
