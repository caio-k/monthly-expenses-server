package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("/initializationData")
    public ResponseEntity<?> getInitializationData(@RequestHeader(value = "userId") Long userId) {
        return ResponseEntity.ok(expenseService.getInitializationData(userId));
    }

    @GetMapping("/byMonthAndYear")
    public ResponseEntity<?> getByMonthAndYear(@RequestHeader(value = "userId") Long userId,
                                               @RequestParam int monthNumber,
                                               @RequestParam int yearNumber) {
        return ResponseEntity.ok(expenseService.getByMonthAndYear(userId, monthNumber, yearNumber));
    }
}
