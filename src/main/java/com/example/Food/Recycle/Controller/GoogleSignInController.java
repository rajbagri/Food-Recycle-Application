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

    // Firebase Google Sign-In
    @PostMapping("/register")
    public ResponseEntity<?> registerUserWithFirebase(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        if (idToken == null || idToken.isEmpty()) {
            return ResponseEntity.badRequest().body("idToken is missing");
        }

        try {
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = token.getUid();
            String email = token.getEmail();
            String name = token.getName();

            User user = new User(name, email);
            user.setFirebaseUid(uid);

            userService.saveIfNotExists(user);
            return ResponseEntity.ok("User registered via Firebase successfully");

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Firebase Token: " + e.getMessage());
        }
    }

    // Basic email/password Sign-In
    @PostMapping("/login")
    public ResponseEntity<?> loginWithEmailPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && password.equals(user.get().getPassword())) {
            return ResponseEntity.ok("User logged in successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // Basic email/password Registration
    @PostMapping("/signup")
    public ResponseEntity<?> signupWithEmailPassword(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully with email/password");
    }
}
