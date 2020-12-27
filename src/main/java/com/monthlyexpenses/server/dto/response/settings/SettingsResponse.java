package com.monthlyexpenses.server.dto.response.settings;

import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;

import java.util.List;

public class SettingsResponse {

    List<ExpenseTypeResponse> expenseTypes;
    List<YearResponse> years;

    public SettingsResponse(List<ExpenseTypeResponse> expenseTypes, List<YearResponse> years) {
        this.expenseTypes = expenseTypes;
        this.years = years;
    }

    public List<ExpenseTypeResponse> getExpenseTypes() {
        return expenseTypes;
    }

    public void setExpenseTypes(List<ExpenseTypeResponse> expenseTypes) {
        this.expenseTypes = expenseTypes;
    }

    public List<YearResponse> getYears() {
        return years;
    }

    public void setYears(List<YearResponse> years) {
        this.years = years;
    }
}
