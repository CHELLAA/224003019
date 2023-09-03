package com.example.number.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class NumberController {

    @GetMapping("/numbers")
    public Map<String, List<Integer>> getNumbers(@RequestParam("url") List<String> urls) {
        List<Integer> allNumbers = new ArrayList<>();

        for (String url : urls) {
            try {
                List<Integer> numbers = fetchNumbersFromUrl(url);
                allNumbers.addAll(numbers);
            } catch (IOException e) {
                // Handle exceptions or log errors here if needed
                System.err.println("Error fetching numbers from URL: " + url);
            }
        }

        // Remove duplicates and sort the numbers
        List<Integer> sortedUniqueNumbers = allNumbers.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        Map<String, List<Integer>> response = new HashMap<>();
        response.put("numbers", sortedUniqueNumbers);
        return response;
    }

    private List<Integer> fetchNumbersFromUrl(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == 200) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();

            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }

            scanner.close();

            // Parse JSON response to extract numbers
            // You'll need to implement this part based on the JSON structure of the response

            // For example, assuming the JSON structure is like this:
            // { "numbers": [1, 2, 3, 5, 8, 13] }
            // You can use a JSON library (e.g., Jackson) to deserialize it.
            // Here, we are assuming a simple regex-based approach to extract numbers.

            List<Integer> numbers = extractNumbersFromJson(response.toString());
            return numbers;
        } else {
            // Handle HTTP error responses here
            throw new IOException("HTTP request failed with status code: " + connection.getResponseCode());
        }
    }

    // Implement the logic to extract numbers from JSON here
    private List<Integer> extractNumbersFromJson(String json) {
        // You'll need to parse the JSON and extract numbers based on your JSON structure
        // This is a placeholder method, and you should replace it with actual JSON parsing logic
        // For example, using Jackson ObjectMapper to deserialize the JSON.
        // Here, we are using a simple regex-based approach for demonstration purposes.

        List<Integer> numbers = new ArrayList<>();
        String[] parts = json.split(":\\s*|,|\\]");

        for (String part : parts) {
            if (part.matches("\\d+")) {
                numbers.add(Integer.parseInt(part));
            }
        }

        return numbers;
    }
}

