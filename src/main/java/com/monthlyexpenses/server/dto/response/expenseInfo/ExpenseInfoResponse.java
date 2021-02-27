package com.monthlyexpenses.server.dto.response.expenseInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseInfoResponse {

    private Long id;
    private String name;
    private float price;
    private boolean paid;
    private Integer month;
    private Integer year;
    private Long expenseTypeId;

    public ExpenseInfoResponse(Long id, String name, float price, boolean paid, Integer month, Integer year, Long expenseTypeId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.paid = paid;
        this.month = month;
        this.year = year;
        this.expenseTypeId = expenseTypeId;
    }
}
