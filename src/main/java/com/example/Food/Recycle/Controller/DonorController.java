package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.service.DonorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private DonorService donorService;

    /**
     * Registers a new donor
     */
    @PostMapping("/register")
    public ResponseEntity<?> createDonor(@RequestBody Donor donor, @RequestParam String userId) {
        try {
            // Convert hex string to ObjectId
            ObjectId objectId = new ObjectId(userId);
            donor.setUserId(objectId);
            Donor savedDonor = donorService.saveDonor(donor);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of(
                            "donorId", savedDonor.getId().toHexString(),
                            "message", "Donor registered successfully"
                    )
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", "Invalid userId format")
            );
        }
    }

    /**
     * Get all donors
     */
    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonor());
    }

    /**
     * Get donor by userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getDonorByUserId(@PathVariable String userId) {
        try {
            ObjectId objectId = new ObjectId(userId);
            Optional<Donor> donorOptional = donorService.findDonorByUserId(objectId);

            return donorOptional
                    .map(donor -> ResponseEntity.ok(donor))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            (Donor) Map.of("error", "Donor not found for userId: " + userId)
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", "Invalid userId format")
            );
        }
    }

    /**
     * Get donors by location
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Donor>> getDonorsByLocation(@PathVariable String location) {
        return ResponseEntity.ok(donorService.findDonorByLocation(location));
    }

    /**
     * Get donor by donorId
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDonorById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Optional<Donor> donorOptional = donorService.findDonorById(objectId);

            return donorOptional
                    .map(donor -> ResponseEntity.ok(donor))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            (Donor) Map.of("error", "Donor not found for id: " + id)
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", "Invalid donorId format")
            );
        }
    }

    /**
     * Delete donor by donorId
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            donorService.deleteById(objectId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("error", "Invalid donorId format")
            );
        }
    }
}
