package com.example.number.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trains")
public class TrainController {

    @GetMapping
    public ResponseEntity<String> getTrainSchedules() {
        try {
            // Define the URL and token
            String apiUrl = "http://20.244.56.144/train/trains";
            String authToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2OTM3MjIzNTEsImNvbXBhbnlOYW1lIjoiVHJhaW4gQ2VudHJhbCIsImNsaWVudElEIjoiODJjNmYzMmYtZTRkMi00NTkxLWEzNDYtOTAyMzhlMTg1YWZlIiwib3duZXJOYW1lIjoiIiwib3duZXJFbWFpbCI6IiIsInJvbGxObyI6IjIyNDAwMzAxOSJ9.TcFhODctSnerDvuxLmbCTj17S5FqLf9KpqD7ilfU-SA";

            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Set the authorization header
            connection.setRequestProperty("Authorization", authToken);

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and process the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Return the response as a ResponseEntity
                return ResponseEntity.ok(response.toString());
            } else {
                // Handle the error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching train schedules.");
            }
        } catch (Exception e) {
            // Handle exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching train schedules.");
        }
    }
}
