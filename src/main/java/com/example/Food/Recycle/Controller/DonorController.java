package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.service.DonorService;
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
        donor.setUserId(userId); // Set userId directly as String
        Donor savedDonor = donorService.saveDonor(donor);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "donorId", savedDonor.getId(),
                        "message", "Donor registered successfully"
                )
        );
    }

    /**
     * Get all donors
     */
    @GetMapping
    public ResponseEntity<?> getAllDonors() {
        List<Donor> donors = donorService.getAllDonor();
        return ResponseEntity.ok(donors);
    }

    /**
     * Get donor by userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getDonorByUserId(@PathVariable String userId) {
        Optional<Donor> donorOptional = donorService.findDonorByUserId(userId);

        return donorOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        (Donor) Map.of("error", "Donor not found for userId: " + userId)
                ));
    }

    /**
     * Get donors by location
     */
    @GetMapping("/location/{location}")
    public ResponseEntity<?> getDonorsByLocation(@PathVariable String location) {
        List<Donor> donors = donorService.findDonorByLocation(location);
        return ResponseEntity.ok(donors);
    }

    /**
     * Get donor by donorId
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDonorById(@PathVariable String id) {
        Optional<Donor> donorOptional = donorService.findDonorById(id);

        return donorOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        (Donor) Map.of("error", "Donor not found for id: " + id)
                ));
    }

    /**
     * Delete donor by donorId
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        donorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
