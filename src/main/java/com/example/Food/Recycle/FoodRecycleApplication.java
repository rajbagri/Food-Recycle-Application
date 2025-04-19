package com.example.Food.Recycle;

import com.example.Food.Recycle.config.FirebaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodRecycleApplication {

	public static void main(String[] args) {
		FirebaseConfig.init();
		SpringApplication.run(FoodRecycleApplication.class, args);
	}

}
