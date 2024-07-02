package com.example.FirstQuestion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class NumberService {

    private static final Logger logger = LoggerFactory.getLogger(NumberService.class);

    private static final int WINDOW_SIZE = 10;
    private Deque<Integer> numberWindow = new LinkedList<>();

    public Map<String, Object> processNumbers(String numberId) {
        Map<String, Object> response = new HashMap<>();

        List<Integer> newNumbers = fetchNumbers(numberId);

        logger.info("New numbers fetched: {}", newNumbers);

        List<Integer> prevState = new ArrayList<>(numberWindow);

        for (int num : newNumbers) {
            if (!numberWindow.contains(num)) {
                if (numberWindow.size() >= WINDOW_SIZE) {
                    numberWindow.pollFirst();
                }
                numberWindow.addLast(num);
            }
        }

        List<Integer> currState = new ArrayList<>(numberWindow);
        double avg = currState.stream().mapToInt(Integer::intValue).average().orElse(0.0);

        response.put("numbers", newNumbers);
        response.put("windowPrevState", prevState);
        response.put("windowCurrState", currState);
        response.put("avg", avg);

        logger.info("Previous window state: {}", prevState);
        logger.info("Current window state: {}", currState);
        logger.info("Current average: {}", avg);

        return response;
    }

    private List<Integer> fetchNumbers(String numberId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Integer>> future = executor.submit(() -> generateNumbers(numberId));

        try {
            return future.get(500, TimeUnit.MILLISECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            logger.error("Error fetching numbers: ", e);
            return Collections.emptyList();
        } finally {
            executor.shutdown();
        }
    }

    private List<Integer> generateNumbers(String numberId) {
        switch (numberId) {
            case "p":
                return generatePrimes(10);
            case "f":
                return generateFibonacci(10);
            case "e":
                return generateEvenNumbers(10);
            case "r":
                return generateRandomNumbers(10);
            default:
                throw new IllegalArgumentException("Invalid number ID");
        }
    }

    private List<Integer> generatePrimes(int count) {
        List<Integer> primes = new ArrayList<>();
        int num = 2;
        while (primes.size() < count) {
            if (isPrime(num)) {
                primes.add(num);
            }
            num++;
        }
        return primes;
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(num); i += 2) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private List<Integer> generateFibonacci(int count) {
        List<Integer> fibonacci = new ArrayList<>();
        if (count > 0) fibonacci.add(0);
        if (count > 1) fibonacci.add(1);
        for (int i = 2; i < count; i++) {
            fibonacci.add(fibonacci.get(i - 1) + fibonacci.get(i - 2));
        }
        return fibonacci;
    }

    private List<Integer> generateEvenNumbers(int count) {
        List<Integer> evenNumbers = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            evenNumbers.add(i * 2);
        }
        return evenNumbers;
    }

    private List<Integer> generateRandomNumbers(int count) {
        List<Integer> randomNumbers = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            randomNumbers.add(rand.nextInt(100));
        }
        return randomNumbers;
    }
}
