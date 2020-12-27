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

    @GetMapping("/get")
    public ResponseEntity<?> getInitializationData(@RequestHeader(value = "userId") Long userId) {
        return expenseService.getInitializationData(userId);
    }
}
