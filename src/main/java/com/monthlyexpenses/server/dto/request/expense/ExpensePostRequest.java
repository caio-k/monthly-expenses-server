package com.monthlyexpenses.server.dto.request.expense;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ExpensePostRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    @NotNull(message = "{yearId.not.null}")
    private Long yearId;

    @NotNull(message = "{month.not.null}")
    private Integer monthNumber;

    @NotBlank(message = "{expenseName.not.blank}")
    private String name;

    @NotNull(message = "{expensePrice.not.null}")
    private float price;

    @NotNull(message = "{expensePaid.not.null}")
    private boolean paid;

    @NotNull(message = "{expenseTypeId.not.null}")
    private Long expenseTypeId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
}
