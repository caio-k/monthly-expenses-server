package com.monthlyexpenses.server.dto.response.expense;

import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExpenseResponseUpdate {

    List<ExpenseInfoResponse> expenseInfos;
    InitialMoneyResponse initialMoney;

    public ExpenseResponseUpdate(List<ExpenseInfoResponse> expenseInfos, InitialMoneyResponse initialMoney) {
        this.expenseInfos = expenseInfos;
        this.initialMoney = initialMoney;
    }
}
