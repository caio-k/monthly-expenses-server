package com.monthlyexpenses.server.dto.request.expenseType;

import javax.validation.constraints.NotNull;

public class ExpenseTypeGetRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
