package com.example.Food.Recycle.service;

import com.example.Food.Recycle.entity.User;
import com.example.Food.Recycle.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteUser(ObjectId id){
        userRepository.deleteById(id);
    }

    public void saveIfNotExists(User user) {
        Optional<User> existingUser = userRepository.findByFirebaseUid(user.getFirebaseUid());
        if (existingUser.isEmpty()) {
            userRepository.save(user);
        }
    }



}
