package com.monthlyexpenses.server.dto.response.expenseType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseTypeResponse {

    private Long id;
    private String name;

    public ExpenseTypeResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
