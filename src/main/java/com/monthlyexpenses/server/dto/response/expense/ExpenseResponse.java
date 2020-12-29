package com.monthlyexpenses.server.dto.response.expense;

import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;

import java.util.List;

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

    public Integer getSelectedYearNumber() {
        return selectedYearNumber;
    }

    public void setSelectedYearNumber(Integer selectedYearNumber) {
        this.selectedYearNumber = selectedYearNumber;
    }

    public Integer getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(Integer selectedMonth) {
        this.selectedMonth = selectedMonth;
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

    public InitialMoneyResponse getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(InitialMoneyResponse initialMoney) {
        this.initialMoney = initialMoney;
    }
}
