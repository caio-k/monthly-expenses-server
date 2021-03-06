package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/getInitializationData")
    public ResponseEntity<?> getInitializationData(@RequestHeader(value = "userId") Long userId) {
        return ResponseEntity.ok(expenseService.getInitializationData(userId));
    }

    @GetMapping("/getByMonthAndYear")
    public ResponseEntity<?> getByMonthAndYear(@RequestHeader(value = "userId") Long userId,
                                               @RequestParam int monthNumber,
                                               @RequestParam int yearNumber) {
        return ResponseEntity.ok(expenseService.getByMonthAndYear(userId, monthNumber, yearNumber));
    }
}
