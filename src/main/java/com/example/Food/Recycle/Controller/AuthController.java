package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupWithEmailPassword(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Email and password are required"
            ));
        }

        // Check if user already exists
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Email already exists"
            ));
        }

        // ⚠️ Don't manually generate ObjectId, let MongoDB handle it
        // If user already has an ID in request, ignore it
        user.setId(null);

        User savedUser = userService.saveUser(user);

        // Return the exact userId saved in DB
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "userId", savedUser.getId(),
                "message", "Registered successfully"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginWithEmailPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Email and password are required"
            ));
        }

        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {
                // Return the exact userId stored in DB
                return ResponseEntity.ok(Map.of(
                        "userId", user.getId(),
                        "message", "Login successful"
                ));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "error", "Invalid email or password"
        ));
    }
}
