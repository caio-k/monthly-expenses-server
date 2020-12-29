package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/getInitializationData")
    public ResponseEntity<?> getInitializationData(@RequestHeader(value = "userId") Long userId) {
        return expenseService.getInitializationData(userId);
    }

    @GetMapping("/getByMonthAndYear")
    public ResponseEntity<?> getByMonthAndYear(@RequestHeader(value = "userId") Long userId,
                                               @RequestParam int monthNumber,
                                               @RequestParam int yearNumber) {
        return expenseService.getByMonthAndYear(userId, monthNumber, yearNumber);
    }
}
