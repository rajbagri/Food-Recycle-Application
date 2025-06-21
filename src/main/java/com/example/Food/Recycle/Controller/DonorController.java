package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.service.DonorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping("/register")
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor, @RequestParam ObjectId userId) {
        donor.setUserId(userId);
        donorService.saveDonor(donor);
        return new ResponseEntity<>(donor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        return new ResponseEntity<>(donorService.getAllDonor(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Donor> getDonorByUserId(@PathVariable ObjectId userId) {
        return donorService.findDonorByUserId(userId)
                .map(donor -> new ResponseEntity<>(donor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Donor>> getDonorsByLocation(@PathVariable String location) {
        return new ResponseEntity<>(donorService.findDonorByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable ObjectId id) {
        return donorService.findDonorById(id)
                .map(donor -> new ResponseEntity<>(donor, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ObjectId id) {
        donorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
