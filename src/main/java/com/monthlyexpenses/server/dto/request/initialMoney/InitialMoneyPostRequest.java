package com.monthlyexpenses.server.dto.request.initialMoney;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InitialMoneyPostRequest {

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;

    @NotNull(message = "{month.not.null}")
    @Min(value = 0, message = "{month.range.values}")
    @Max(value = 11, message = "{month.range.values}")
    private Integer month;

    @NotNull(message = "{initialMoney.not.null}")
    private float initialMoney;
}
