package com.monthlyexpenses.server.controller;

import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypeDeleteRequest;
import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypeGetRequest;
import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypePostRequest;
import com.monthlyexpenses.server.dto.request.expenseType.ExpenseTypePutRequest;
import com.monthlyexpenses.server.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expenseType")
public class ExpenseTypeController {

    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public ExpenseTypeController(ExpenseTypeService expenseTypeService) {
        this.expenseTypeService = expenseTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createExpenseType(@Valid @RequestBody ExpenseTypePostRequest expenseTypePostRequest) {
        return expenseTypeService.create(expenseTypePostRequest.getUserId(), expenseTypePostRequest.getExpenseTypeName());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateExpenseType(@Valid @RequestBody ExpenseTypePutRequest expenseTypePutRequest) {
        return expenseTypeService.update(expenseTypePutRequest.getUserId(), expenseTypePutRequest.getExpenseTypeName(), expenseTypePutRequest.getId());
    }

    @GetMapping("/get")
    public ResponseEntity<?> getExpenseTypes(@Valid @RequestBody ExpenseTypeGetRequest expenseTypeGetRequest) {
        return expenseTypeService.getAllExpenseTypes(expenseTypeGetRequest.getUserId());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteExpenseType(@Valid @RequestBody ExpenseTypeDeleteRequest expenseTypeDeleteRequest) {
        return expenseTypeService.delete(expenseTypeDeleteRequest.getUserId(), expenseTypeDeleteRequest.getId());
    }
}
