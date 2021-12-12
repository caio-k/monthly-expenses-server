package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.expense.ExpensePostRequest;
import com.monthlyexpenses.server.dto.request.expense.ExpensePutRequest;
import com.monthlyexpenses.server.service.ExpenseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/expenseInfo")
@RequiredArgsConstructor
public class ExpenseInfoController {

    private final ExpenseInfoService expenseInfoService;

    @PostMapping
    public ResponseEntity<?> createExpense(@RequestHeader(value = "userId") Long userId,
                                           @Valid @RequestBody ExpensePostRequest expensePostRequest) {
        return ResponseEntity.ok(expenseInfoService.createExpense(userId, expensePostRequest.getName(),
                expensePostRequest.getPrice(), expensePostRequest.isPaid(), expensePostRequest.getExpenseTypeId(),
                expensePostRequest.getMonthNumber(), expensePostRequest.getYearNumber()));
    }

    @PutMapping
    public ResponseEntity<?> updateExpense(@RequestHeader(value = "userId") Long userId,
                                           @Valid @RequestBody ExpensePutRequest expensePutRequest) {
        return ResponseEntity.ok(expenseInfoService.updateExpense(userId, expensePutRequest.getExpenseId(),
                expensePutRequest.getName(), expensePutRequest.getPrice(), expensePutRequest.isPaid(),
                expensePutRequest.getExpenseTypeId()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteExpense(@RequestHeader(value = "userId") Long userId,
                                           @RequestParam Long expenseId) {
        return ResponseEntity.ok(expenseInfoService.deleteExpense(userId, expenseId));
    }
}
