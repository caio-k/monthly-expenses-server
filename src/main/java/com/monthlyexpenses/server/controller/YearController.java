package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.year.YearPostRequest;
import com.monthlyexpenses.server.dto.request.year.YearPutRequest;
import com.monthlyexpenses.server.service.YearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/year")
public class YearController {

    private final YearService yearService;

    @Autowired
    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getYears(@RequestParam Long userId) {
        return yearService.getAllYearsByUserId(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createYear(@Valid @RequestBody YearPostRequest yearPostRequest) {
        return yearService.createYear(yearPostRequest.getUserId(), yearPostRequest.getYearNumber());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateYear(@Valid @RequestBody YearPutRequest yearPutRequest) {
        return yearService.updateYear(yearPutRequest.getUserId(), yearPutRequest.getYearId(), yearPutRequest.getYearNumber());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteYear(@RequestParam Long userId, @RequestParam Long yearId) {
        return yearService.deleteYear(userId, yearId);
    }
}
