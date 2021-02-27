package com.monthlyexpenses.server.dto.response.expense;

import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExpenseResponse {

    Integer selectedYearNumber;
    Integer selectedMonth;
    List<YearResponse> years;
    List<ExpenseTypeResponse> expenseTypes;
    List<ExpenseInfoResponse> expenseInfos;
    InitialMoneyResponse initialMoney;

    public ExpenseResponse(Integer selectedYearNumber, Integer selectedMonth, List<YearResponse> years,
                           List<ExpenseTypeResponse> expenseTypes, List<ExpenseInfoResponse> expenseInfos,
                           InitialMoneyResponse initialMoney) {
        this.selectedYearNumber = selectedYearNumber;
        this.selectedMonth = selectedMonth;
        this.years = years;
        this.expenseTypes = expenseTypes;
        this.expenseInfos = expenseInfos;
        this.initialMoney = initialMoney;
    }
}
