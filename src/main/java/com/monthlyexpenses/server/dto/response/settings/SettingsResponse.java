package com.monthlyexpenses.server.dto.response.settings;

import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SettingsResponse {

    private List<ExpenseTypeResponse> expenseTypes;
    private List<YearResponse> years;
}
