package com.monthlyexpenses.server.dto.request.expenseType;

import javax.validation.constraints.NotNull;

public class ExpenseTypeDeleteRequest {

    @NotNull(message = "{expenseTypeId.not.null}")
    private Long id;

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
