package com.monthlyexpenses.server.dto.request.expenseType;

import javax.validation.constraints.NotBlank;

public class ExpenseTypePostRequest {

    @NotBlank(message = "{expenseTypeName.not.blank}")
    private String expenseTypeName;

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }
}
