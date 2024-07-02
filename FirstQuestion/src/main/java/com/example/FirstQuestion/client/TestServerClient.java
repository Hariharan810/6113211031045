package com.example.FirstQuestion.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class TestServerClient {

    private static final Logger logger = LoggerFactory.getLogger(TestServerClient.class);
    private static final String BASE_URL = "http://20.244.56.144/test/";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Integer> getNumbers(String numberId) {
        String url = BASE_URL + getEndpoint(numberId);
        logger.info("Fetching numbers from URL: {}", url);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        logger.info("Received response: {}", response);
        return (List<Integer>) response.get("numbers");
    }

    private String getEndpoint(String numberId) {
        switch (numberId) {
            case "p":
                return "primes";
            case "f":
                return "fibo";
            case "e":
                return "even";
            case "r":
                return "rand";
            default:
                throw new IllegalArgumentException("Invalid number ID");
        }
    }
}
