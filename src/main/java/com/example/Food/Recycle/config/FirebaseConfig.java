package com.example.Food.Recycle.config;


import org.springframework.beans.factory.annotation.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
@Configuration
public class FirebaseConfig {

    @Value("${FIREBASE_ADMIN_SDK}")
    private String FIREBASE_CONFIG_STRING;

    @PostConstruct
    public void initialize() throws IOException {

        InputStream serviceAccount = new ByteArrayInputStream(FIREBASE_CONFIG_STRING.getBytes());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
