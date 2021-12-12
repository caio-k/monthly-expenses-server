package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypePostRequest;
import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypePutRequest;
import com.monthlyexpenses.server.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/expenseType")
@RequiredArgsConstructor
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @PostMapping
    public ResponseEntity<?> createExpenseType(@RequestHeader(value = "userId") Long userId,
                                               @Valid @RequestBody ExpenseTypePostRequest expenseTypePostRequest) {
        return ResponseEntity.ok(expenseTypeService.create(userId, expenseTypePostRequest.getExpenseTypeName()));
    }

    @PutMapping
    public ResponseEntity<?> updateExpenseType(@RequestHeader(value = "userId") Long userId,
                                               @Valid @RequestBody ExpenseTypePutRequest expenseTypePutRequest) {
        return ResponseEntity.ok(expenseTypeService.update(userId, expenseTypePutRequest.getExpenseTypeName(),
                expenseTypePutRequest.getId()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteExpenseType(@RequestHeader(value = "userId") Long userId,
                                               @RequestParam Long expenseTypeId) {
        return ResponseEntity.ok(expenseTypeService.delete(userId, expenseTypeId));
    }
}
