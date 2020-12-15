package com.monthlyexpenses.server.dto.request.expenseType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ExpenseTypePostRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    @NotBlank(message = "{expenseTypeName.not.blank}")
    private String expenseTypeName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }
}
