package com.monthlyexpenses.server.dto.response.expense;

import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExpenseResponse {

    private Integer selectedYearNumber;
    private Integer selectedMonth;
    private List<YearResponse> years;
    private List<ExpenseTypeResponse> expenseTypes;
    private List<ExpenseInfoResponse> expenseInfos;
    private InitialMoneyResponse initialMoney;
}
