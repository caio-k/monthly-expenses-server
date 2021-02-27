package com.monthlyexpenses.server.dto.request.initialMoney;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InitialMoneyPutRequest {

    @NotNull(message = "{initialMoneyId.not.null}")
    private Long initialMoneyId;

    @NotNull(message = "{initialMoney.not.null}")
    private float initialMoney;
}
