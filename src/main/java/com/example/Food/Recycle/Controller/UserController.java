package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.service.UserService;
import org.bson.types.ObjectId; // For MongoDB ObjectId
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Get user by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ Save user (auto-generate MongoDB ObjectId)
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        if (user.getId() == null) {
            user.setId(new ObjectId().toHexString()); // Auto-generate userId if not provided
        }
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // ✅ Delete user
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
