package com.monthlyexpenses.server.dto.request.expenseType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ExpenseTypePutRequest {

    @NotNull(message = "{expenseTypeId.not.null}")
    private Long id;

    @NotBlank(message = "{expenseTypeName.not.blank}")
    private String expenseTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }
}
