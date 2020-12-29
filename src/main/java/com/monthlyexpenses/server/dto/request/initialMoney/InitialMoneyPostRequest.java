package com.monthlyexpenses.server.dto.request.initialMoney;

import javax.validation.constraints.NotNull;

public class InitialMoneyPostRequest {

    @NotNull(message = "{yearId.not.null}")
    private Long yearId;

    @NotNull(message = "{month.not.null}")
    private Integer month;

    @NotNull(message = "{initialMoney.not.null}")
    private float initialMoney;

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public float getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(float initialMoney) {
        this.initialMoney = initialMoney;
    }
}
