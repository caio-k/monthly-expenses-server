package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.domain.enums.Month;

public class Expense {

    private String name;
    private Float price;
    private Boolean paid;
    private User user;
    private ExpenseType expenseType;
    private Month month;
    private Year year;

    public Expense(String name, Float price, Boolean paid, User user, ExpenseType expenseType, Month month, Year year) {
        this.name = name;
        this.price = price;
        this.paid = paid;
        this.user = user;
        this.expenseType = expenseType;
        this.month = month;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
