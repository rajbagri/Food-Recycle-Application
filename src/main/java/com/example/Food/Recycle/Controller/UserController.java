package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId myId) {
        return userService.getUserById(myId)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public void saveUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId myId) {
        userService.deleteUser(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
