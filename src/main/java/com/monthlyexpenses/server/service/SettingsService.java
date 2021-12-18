package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.settings.SettingsResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final YearService yearService;
    private final ExpenseTypeService expenseTypeService;

    public SettingsResponse getInitializationData(Long customerId) {
        List<YearResponse> yearResponses = yearService.getAllYearsByUserId(customerId);
        List<ExpenseTypeResponse> expenseTypeResponses = expenseTypeService.getAllExpenseTypes(customerId);

        return SettingsResponse.builder()
                .expenseTypes(expenseTypeResponses)
                .years(yearResponses)
                .build();
    }
}
