package com.example.Food.Recycle.service;

import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User saveIfNotExists(User user) {
        Optional<User> existingUser = userRepository.findByFirebaseUid(user.getFirebaseUid());
        if (existingUser.isEmpty()) {
            userRepository.save(user);
        }
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
