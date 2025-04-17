package com.example.Food.Recycle.config;


import org.springframework.beans.factory.annotation.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {


    @Value("${FIREBASE_CONFIG_PATH}")
    private String firebaseConfig;

    @PostConstruct
    public void initializeFirebase() throws IOException {
        InputStream serviceAccount = new ClassPathResource(firebaseConfig).getInputStream();

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase has been initialized.");
        }
    }
}

