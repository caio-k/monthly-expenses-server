package com.monthlyexpenses.server.dto.request.expense;

import javax.validation.constraints.NotNull;

public class ExpenseGetRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    @NotNull(message = "{yearId.not.null}")
    private Long yearId;

    @NotNull(message = "{month.not.null}")
    private Integer monthNumber;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }
}
