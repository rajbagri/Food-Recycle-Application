package com.example.Food.Recycle.Controller;

import com.example.Food.Recycle.entity.Donor;
import com.example.Food.Recycle.entity.FoodItem;
import com.example.Food.Recycle.service.DonorService;
import com.example.Food.Recycle.service.FoodService;
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

    @Autowired
     private FoodService foodService;


    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonor(){
        List<Donor> allDonor = donorService.getAllDonor();
        if(!allDonor.isEmpty()){
            return new ResponseEntity<>(allDonor, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor){


        donorService.saveDonor(donor);

        return new ResponseEntity<>(donor, HttpStatus.CREATED);
    }


    @GetMapping("location/{myLocation}")
    public ResponseEntity<List<Donor>> findDonorByLocation(@PathVariable String myLocation){
        List<Donor> donorByLocation = donorService.findDonorByLocation(myLocation);
        if(donorByLocation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(donorByLocation, HttpStatus.OK);
    }

    @GetMapping("id/{myid}")
    public Optional<Donor> findDonorById(@PathVariable ObjectId myid){
        return donorService.findDonorById(myid);
    }

    @DeleteMapping("/id/{myId}")
    public void deleteById(@PathVariable ObjectId myId){
        donorService.deleteById(myId);
    }

}
