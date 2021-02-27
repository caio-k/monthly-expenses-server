package com.monthlyexpenses.server.dto.response.initialMoney;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitialMoneyResponse {

    private Long initialMoneyId;
    private Integer month;
    private Integer year;
    private float initialMoney;

    public InitialMoneyResponse(Long initialMoneyId, Integer month, Integer year, float initialMoney) {
        this.initialMoneyId = initialMoneyId;
        this.month = month;
        this.year = year;
        this.initialMoney = initialMoney;
    }
}
