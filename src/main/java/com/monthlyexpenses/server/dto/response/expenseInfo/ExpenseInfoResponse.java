package com.monthlyexpenses.server.dto.response.expenseInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseInfoResponse {

    private Long id;
    private String name;
    private float price;
    private boolean paid;
    private Integer month;
    private Integer year;
    private Long expenseTypeId;
}
