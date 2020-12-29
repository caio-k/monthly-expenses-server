package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.expense.ExpensePostRequest;
import com.monthlyexpenses.server.dto.request.expense.ExpensePutRequest;
import com.monthlyexpenses.server.service.ExpenseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expenseInfo")
public class ExpenseInfoController {

    private final ExpenseInfoService expenseInfoService;

    @Autowired
    public ExpenseInfoController(ExpenseInfoService expenseInfoService) {
        this.expenseInfoService = expenseInfoService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> createExpense(@RequestHeader(value = "userId") Long userId,
                                           @Valid @RequestBody ExpensePostRequest expensePostRequest) {
        return expenseInfoService.createExpense(userId, expensePostRequest.getName(), expensePostRequest.getPrice(),
                expensePostRequest.isPaid(), expensePostRequest.getExpenseTypeId(), expensePostRequest.getMonthNumber(),
                expensePostRequest.getYearId());
    }

    @PutMapping("/put")
    public ResponseEntity<?> updateExpense(@RequestHeader(value = "userId") Long userId,
                                           @Valid @RequestBody ExpensePutRequest expensePutRequest) {
        return expenseInfoService.updateExpense(userId, expensePutRequest.getExpenseId(), expensePutRequest.getName(),
                expensePutRequest.getPrice(), expensePutRequest.isPaid(), expensePutRequest.getExpenseTypeId(),
                expensePutRequest.getMonthNumber(), expensePutRequest.getYearId());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteExpense(@RequestHeader(value = "userId") Long userId,
                                           @RequestParam Long expenseId) {
        return expenseInfoService.deleteExpense(userId, expenseId);
    }
}
