package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.expense.ExpenseDeleteRequest;
import com.monthlyexpenses.server.dto.request.expense.ExpenseGetRequest;
import com.monthlyexpenses.server.dto.request.expense.ExpensePostRequest;
import com.monthlyexpenses.server.dto.request.expense.ExpensePutRequest;
import com.monthlyexpenses.server.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> getExpensesByMonthAndYear(@Valid @RequestBody ExpenseGetRequest expenseGetRequest) {
        return expenseService.getExpensesByMonthAndYear(expenseGetRequest.getUserId(),
                expenseGetRequest.getMonthNumber(), expenseGetRequest.getYearId());
    }

    @PostMapping("/post")
    public ResponseEntity<?> createExpense(@Valid @RequestBody ExpensePostRequest expensePostRequest) {
        return expenseService.createExpense(expensePostRequest.getUserId(), expensePostRequest.getName(),
                expensePostRequest.getPrice(), expensePostRequest.isPaid(), expensePostRequest.getExpenseTypeId(),
                expensePostRequest.getMonthNumber(), expensePostRequest.getYearId());
    }

    @PutMapping("/put")
    public ResponseEntity<?> updateExpense(@Valid @RequestBody ExpensePutRequest expensePutRequest) {
        return expenseService.updateExpense(expensePutRequest.getUserId(), expensePutRequest.getExpenseId(),
                expensePutRequest.getName(), expensePutRequest.getPrice(), expensePutRequest.isPaid(),
                expensePutRequest.getExpenseTypeId(), expensePutRequest.getMonthNumber(), expensePutRequest.getYearId());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteExpense(@Valid @RequestBody ExpenseDeleteRequest expenseDeleteRequest) {
        return expenseService.deleteExpense(expenseDeleteRequest.getUserId(), expenseDeleteRequest.getExpenseId());
    }
}
