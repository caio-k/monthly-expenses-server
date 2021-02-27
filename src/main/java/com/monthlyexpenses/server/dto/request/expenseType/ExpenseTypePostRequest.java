package com.monthlyexpenses.server.dto.request.expenseType;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ExpenseTypePostRequest {

    @NotBlank(message = "{expenseTypeName.not.blank}")
    private String expenseTypeName;
}
