package com.example.Food.Recycle.Controller;


import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class GoogleSignInController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");

        if (idToken == null || idToken.isEmpty()) {
            return ResponseEntity.badRequest().body("idToken is missing");
        }

        try{
            FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);

            String Uid = token.getUid();
            String email = token.getEmail();
            String name = token.getName();

            User user = new User(name, email);
            user.setFirebaseUid(Uid);

            userService.saveIfNotExists(user);

            return ResponseEntity.ok("user registered successfully");


        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Firebase Token");
        }

    }

}
