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

    @PostMapping("/register")
    public ResponseEntity<?> createDonor(@RequestBody Donor donor) {
        if (donor.getUserId() == null || donor.getUserId().isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "userId is required to register donor")
            );
        }

        Donor savedDonor = donorService.saveDonor(donor);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "donorId", savedDonor.getId(),
                        "userId", donor.getUserId(),
                        "message", "Donor registered successfully"
                )
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllDonors() {
        List<Donor> donors = donorService.getAllDonor();
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getDonorByUserId(@PathVariable String userId) {
        Optional<Donor> donorOptional = donorService.findDonorByUserId(userId);

        if (donorOptional.isPresent()) {
            return ResponseEntity.ok(donorOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Donor not found for userId: " + userId));
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> getDonorsByLocation(@PathVariable String location) {
        List<Donor> donors = donorService.findDonorByLocation(location);
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDonorById(@PathVariable String id) {
        Optional<Donor> donorOptional = donorService.findDonorById(id);

        if (donorOptional.isPresent()) {
            return ResponseEntity.ok(donorOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Donor not found for id: " + id));
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        donorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
