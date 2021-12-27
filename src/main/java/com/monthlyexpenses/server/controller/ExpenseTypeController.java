package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypePostRequest;
import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypePutRequest;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.service.ExpenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/expense-type")
@RequiredArgsConstructor
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @PostMapping
    public ResponseEntity<ExpenseTypeResponse> createExpenseType(@RequestHeader(value = "userId") Long userId,
                                                                 @Valid @RequestBody ExpenseTypePostRequest expenseTypePostRequest) {
        return ok(expenseTypeService.create(userId, expenseTypePostRequest.getExpenseTypeName()));
    }

    @PutMapping
    public ResponseEntity<ExpenseTypeResponse> updateExpenseType(@RequestHeader(value = "userId") Long userId,
                                                                 @Valid @RequestBody ExpenseTypePutRequest expenseTypePutRequest) {
        return ok(expenseTypeService.update(userId, expenseTypePutRequest.getExpenseTypeName(),
                expenseTypePutRequest.getId()));
    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteExpenseType(@RequestHeader(value = "userId") Long userId,
                                                             @RequestParam Long expenseTypeId) {
        return ok(expenseTypeService.delete(userId, expenseTypeId));
    }
}
