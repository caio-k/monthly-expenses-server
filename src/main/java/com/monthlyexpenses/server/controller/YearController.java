package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.year.YearPostRequest;
import com.monthlyexpenses.server.dto.request.year.YearPutRequest;
import com.monthlyexpenses.server.service.YearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/year")
@RequiredArgsConstructor
public class YearController {

    private final YearService yearService;

    @PostMapping
    public ResponseEntity<?> createYear(@RequestHeader(value = "userId") Long userId,
                                        @Valid @RequestBody YearPostRequest yearPostRequest) {
        return ResponseEntity.ok(yearService.createYear(userId, yearPostRequest.getYearNumber()));
    }

    @PutMapping
    public ResponseEntity<?> updateYear(@RequestHeader(value = "userId") Long userId,
                                        @Valid @RequestBody YearPutRequest yearPutRequest) {
        return ResponseEntity.ok(yearService.updateYear(userId, yearPutRequest.getYearId(),
                yearPutRequest.getYearNumber()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteYear(@RequestHeader(value = "userId") Long userId, @RequestParam Long yearId) {
        return ResponseEntity.ok(yearService.deleteYear(userId, yearId));
    }
}
