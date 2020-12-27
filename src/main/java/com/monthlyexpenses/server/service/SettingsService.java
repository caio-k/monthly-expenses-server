package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.settings.SettingsResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsService {

    private final YearService yearService;

    private final ExpenseTypeService expenseTypeService;

    @Autowired
    public SettingsService(YearService yearService, ExpenseTypeService expenseTypeService) {
        this.yearService = yearService;
        this.expenseTypeService = expenseTypeService;
    }

    public ResponseEntity<?> getInitializationData(Long userId) {
        List<YearResponse> yearResponses = yearService.getAllYearsByUserId(userId);
        List<ExpenseTypeResponse> expenseTypeResponses = expenseTypeService.getAllExpenseTypes(userId);
        return ResponseEntity.ok(new SettingsResponse(expenseTypeResponses, yearResponses));
    }
}
