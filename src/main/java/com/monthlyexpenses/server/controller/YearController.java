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

    @PostMapping("/create")
    public ResponseEntity<?> createYear(@RequestHeader(value = "userId") Long userId,
                                        @Valid @RequestBody YearPostRequest yearPostRequest) {
        return yearService.createYear(userId, yearPostRequest.getYearNumber());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateYear(@RequestHeader(value = "userId") Long userId,
                                        @Valid @RequestBody YearPutRequest yearPutRequest) {
        return yearService.updateYear(userId, yearPutRequest.getYearId(), yearPutRequest.getYearNumber());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteYear(@RequestHeader(value = "userId") Long userId, @RequestParam Long yearId) {
        return yearService.deleteYear(userId, yearId);
    }
}
