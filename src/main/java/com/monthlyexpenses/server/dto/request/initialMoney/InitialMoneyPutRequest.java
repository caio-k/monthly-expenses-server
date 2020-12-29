package com.monthlyexpenses.server.dto.request.initialMoney;

import javax.validation.constraints.NotNull;

public class InitialMoneyPutRequest {

    @NotNull(message = "{initialMoneyId.not.null}")
    private Long initialMoneyId;

    @NotNull(message = "{initialMoney.not.null}")
    private float initialMoney;

    public Long getInitialMoneyId() {
        return initialMoneyId;
    }

    public void setInitialMoneyId(Long initialMoneyId) {
        this.initialMoneyId = initialMoneyId;
    }

    public float getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(float initialMoney) {
        this.initialMoney = initialMoney;
    }
}
