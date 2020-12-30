package com.monthlyexpenses.server.dto.request.initialMoney;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class InitialMoneyPostRequest {

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;

    @NotNull(message = "{month.not.null}")
    private Integer month;

    @NotNull(message = "{initialMoney.not.null}")
    private float initialMoney;

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
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
