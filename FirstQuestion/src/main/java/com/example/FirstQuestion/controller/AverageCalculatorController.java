package com.example.FirstQuestion.controller;

import com.example.FirstQuestion.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AverageCalculatorController {

    @Autowired
    private NumberService numberService;

    @GetMapping("/numbers/{numberId}")
    public Map<String, Object> getNumbers(@PathVariable String numberId) {
        return numberService.processNumbers(numberId);
    }
}
