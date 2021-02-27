package com.monthlyexpenses.server.dto.request.expense;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ExpensePutRequest {

    @NotNull(message = "{expenseId.not.null}")
    private Long expenseId;

    @NotBlank(message = "{expenseName.not.blank}")
    private String name;

    @NotNull(message = "{expensePrice.not.null}")
    private float price;

    @NotNull(message = "{expensePaid.not.null}")
    private boolean paid;

    @NotNull(message = "{expenseTypeId.not.null}")
    private Long expenseTypeId;
}
