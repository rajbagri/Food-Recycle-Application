package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.service.UserService;
import com.example.Food.Recycle.service.DonorService;
import org.bson.types.ObjectId;
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

    @Autowired
    private DonorService donorService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupWithEmailPassword(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
        }

        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Email already exists"));
        }

        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(new ObjectId().toHexString());
        }

        User savedUser = userService.saveUser(user);
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
            return ResponseEntity.badRequest().body(Map.of("error", "Email and password are required"));
        }

        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (password.equals(user.getPassword())) {
                Optional<Donor> donorOptional = donorService.findByUserId(user.getId());
                String donorId = donorOptional.map(Donor::getId).orElse(null);
                return ResponseEntity.ok(Map.of(
                        "userId", user.getId(),
                        "donorId", donorId,
                        "message", "Login successful"
                ));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid email or password"));
    }
}
