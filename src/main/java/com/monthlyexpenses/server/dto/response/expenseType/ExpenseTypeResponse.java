package com.monthlyexpenses.server.dto.response.expenseType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseTypeResponse {

    private Long id;
    private String name;
}
