package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class GoogleSignInController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerWithFirebase(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        if (idToken == null || idToken.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Missing idToken"
            ));
        }

        try {
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = token.getUid();
            String email = token.getEmail();
            String name = token.getName();

            User user = new User(name, email);
            user.setFirebaseUid(uid);

            User savedUser = userService.saveIfNotExists(user);

            return ResponseEntity.ok(Map.of(
                    "userId", savedUser.getId(),
                    "message", "Registered with Firebase"
            ));

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Invalid Firebase token: " + e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Server error: " + e.getMessage()
            ));
        }
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


    @PostMapping("/signup")
    public ResponseEntity<?> signupWithEmailPassword(@RequestBody User user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Email and password are required"
            ));
        }

        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Email already exists"
            ));
        }

        User savedUser = userService.saveUser(user);

        return ResponseEntity.ok(Map.of(
                "userId", savedUser.getId(),
                "message", "Registered successfully"
        ));
    }
}
