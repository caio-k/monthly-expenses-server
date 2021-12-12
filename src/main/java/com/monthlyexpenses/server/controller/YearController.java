package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.year.YearPostRequest;
import com.monthlyexpenses.server.dto.request.year.YearPutRequest;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.service.YearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/year")
@RequiredArgsConstructor
public class YearController {

    private final YearService yearService;

    @PostMapping
    public ResponseEntity<YearResponse> createYear(@RequestHeader(value = "userId") Long userId,
                                                   @Valid @RequestBody YearPostRequest yearPostRequest) {
        return ok(yearService.createYear(userId, yearPostRequest.getYearNumber()));
    }

    @PutMapping
    public ResponseEntity<YearResponse> updateYear(@RequestHeader(value = "userId") Long userId,
                                                   @Valid @RequestBody YearPutRequest yearPutRequest) {
        return ok(yearService.updateYear(userId, yearPutRequest.getYearId(),
                yearPutRequest.getYearNumber()));
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteYear(@RequestHeader(value = "userId") Long userId, @RequestParam Long yearId) {
        return ok(yearService.deleteYear(userId, yearId));
    }
}
