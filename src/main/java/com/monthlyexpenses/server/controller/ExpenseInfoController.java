package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.expense.ExpensePostRequest;
import com.monthlyexpenses.server.dto.request.expense.ExpensePutRequest;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.service.ExpenseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/expenseInfo")
@RequiredArgsConstructor
public class ExpenseInfoController {

    private final ExpenseInfoService expenseInfoService;

    @PostMapping
    public ResponseEntity<ExpenseInfoResponse> createExpense(@RequestHeader(value = "userId") Long userId,
                                                             @Valid @RequestBody ExpensePostRequest expensePostRequest) {
        return ok(expenseInfoService.createExpense(userId, expensePostRequest.getName(),
                expensePostRequest.getPrice(), expensePostRequest.isPaid(), expensePostRequest.getExpenseTypeId(),
                expensePostRequest.getMonthNumber(), expensePostRequest.getYearNumber()));
    }

    @PutMapping
    public ResponseEntity<ExpenseInfoResponse> updateExpense(@RequestHeader(value = "userId") Long userId,
                                                             @Valid @RequestBody ExpensePutRequest expensePutRequest) {
        return ok(expenseInfoService.updateExpense(userId, expensePutRequest.getExpenseId(),
                expensePutRequest.getName(), expensePutRequest.getPrice(), expensePutRequest.isPaid(),
                expensePutRequest.getExpenseTypeId()));
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteExpense(@RequestHeader(value = "userId") Long userId,
                                                         @RequestParam Long expenseId) {
        return ok(expenseInfoService.deleteExpense(userId, expenseId));
    }
}
