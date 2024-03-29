package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.response.expense.ExpenseResponse;
import com.monthlyexpenses.server.dto.response.expense.ExpenseResponseUpdate;
import com.monthlyexpenses.server.service.ExpenseCompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseCompositionService expenseCompositionService;

    @GetMapping("/initialization-data")
    public ResponseEntity<ExpenseResponse> getInitializationData(@RequestHeader(value = "userId") Long userId) {
        return ok(expenseCompositionService.getInitializationData(userId));
    }

    @GetMapping("/by-month-and-year")
    public ResponseEntity<ExpenseResponseUpdate> getByMonthAndYear(@RequestHeader(value = "userId") Long userId,
                                                                   @RequestParam int monthNumber,
                                                                   @RequestParam int yearNumber) {
        return ok(expenseCompositionService.getAllExpensesAndInitialMoneyByMonthAndYear(userId, monthNumber, yearNumber));
    }
}
