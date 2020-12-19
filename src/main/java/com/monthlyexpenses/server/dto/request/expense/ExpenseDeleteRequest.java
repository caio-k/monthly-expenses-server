package com.monthlyexpenses.server.dto.request.expense;

import javax.validation.constraints.NotNull;

public class ExpenseDeleteRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    @NotNull(message = "{expenseId.not.null}")
    private Long expenseId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }
}
