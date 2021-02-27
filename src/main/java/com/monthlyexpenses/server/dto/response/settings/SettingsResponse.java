package com.monthlyexpenses.server.dto.response.settings;

import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettingsResponse {

    List<ExpenseTypeResponse> expenseTypes;
    List<YearResponse> years;

    public SettingsResponse(List<ExpenseTypeResponse> expenseTypes, List<YearResponse> years) {
        this.expenseTypes = expenseTypes;
        this.years = years;
    }
}
