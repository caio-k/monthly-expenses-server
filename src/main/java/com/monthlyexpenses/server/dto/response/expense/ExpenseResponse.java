package com.monthlyexpenses.server.dto.response.expense;

import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;

import java.util.List;

public class ExpenseResponse {

    List<YearResponse> years;
    List<ExpenseTypeResponse> expenseTypes;
    List<ExpenseInfoResponse> expenseInfos;
    List<InitialMoneyResponse> initialMoneys;

    public ExpenseResponse(List<YearResponse> years, List<ExpenseTypeResponse> expenseTypes,
                           List<ExpenseInfoResponse> expenseInfos, List<InitialMoneyResponse> initialMoneys) {
        this.years = years;
        this.expenseTypes = expenseTypes;
        this.expenseInfos = expenseInfos;
        this.initialMoneys = initialMoneys;
    }

    public List<YearResponse> getYears() {
        return years;
    }

    public void setYears(List<YearResponse> years) {
        this.years = years;
    }

    public List<ExpenseTypeResponse> getExpenseTypes() {
        return expenseTypes;
    }

    public void setExpenseTypes(List<ExpenseTypeResponse> expenseTypes) {
        this.expenseTypes = expenseTypes;
    }

    public List<ExpenseInfoResponse> getExpenseInfos() {
        return expenseInfos;
    }

    public void setExpenseInfos(List<ExpenseInfoResponse> expenseInfos) {
        this.expenseInfos = expenseInfos;
    }

    public List<InitialMoneyResponse> getInitialMoneys() {
        return initialMoneys;
    }

    public void setInitialMoneys(List<InitialMoneyResponse> initialMoneys) {
        this.initialMoneys = initialMoneys;
    }
}
