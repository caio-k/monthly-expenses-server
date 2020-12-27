package com.monthlyexpenses.server.dto.response.expenseInfo;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }
}
