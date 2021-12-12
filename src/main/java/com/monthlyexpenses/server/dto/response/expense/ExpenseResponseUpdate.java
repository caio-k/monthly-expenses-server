package com.monthlyexpenses.server.dto.response.expense;

import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExpenseResponseUpdate {

    private List<ExpenseInfoResponse> expenseInfos;
    private InitialMoneyResponse initialMoney;
}
