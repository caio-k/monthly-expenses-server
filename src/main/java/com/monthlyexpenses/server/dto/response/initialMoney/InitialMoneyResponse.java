package com.monthlyexpenses.server.dto.response.initialMoney;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InitialMoneyResponse {

    private Long initialMoneyId;
    private Integer month;
    private Integer year;
    private float initialMoney;
}
