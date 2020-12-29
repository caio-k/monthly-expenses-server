package com.monthlyexpenses.server.dto.response.expense;

import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;

import java.util.List;

public class ExpenseResponseUpdate {

    List<ExpenseInfoResponse> expenseInfos;
    InitialMoneyResponse initialMoney;

    public ExpenseResponseUpdate(List<ExpenseInfoResponse> expenseInfos, InitialMoneyResponse initialMoney) {
        this.expenseInfos = expenseInfos;
        this.initialMoney = initialMoney;
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
