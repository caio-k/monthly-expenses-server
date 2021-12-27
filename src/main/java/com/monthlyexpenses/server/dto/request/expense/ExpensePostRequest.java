package com.monthlyexpenses.server.dto.request.expense;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ExpensePostRequest {

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;

    @NotNull(message = "{month.not.null}")
    @Min(value = 0, message = "{month.range.values}")
    @Max(value = 11, message = "{month.range.values}")
    private Integer monthNumber;

    @NotBlank(message = "{expenseName.not.blank}")
    private String name;

    @NotNull(message = "{expensePrice.not.null}")
    private float price;

    @NotNull(message = "{expensePaid.not.null}")
    private boolean paid;

    @NotNull(message = "{expenseTypeId.not.null}")
    private Long expenseTypeId;
}
