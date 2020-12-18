package com.monthlyexpenses.server.dto.request.initialMoney;

import javax.validation.constraints.NotNull;

public class InitialMoneyGetRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    @NotNull(message = "{yearId.not.null}")
    private Long yearId;

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
}
