package com.monthlyexpenses.server.dto.request.expenseType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ExpenseTypePutRequest {

    @NotNull(message = "{expenseTypeId.not.null}")
    private Long id;

    @NotBlank(message = "{expenseTypeName.not.blank}")
    private String expenseTypeName;
}
