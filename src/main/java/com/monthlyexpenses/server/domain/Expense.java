package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.app.exception.InvalidArgumentException;
import com.monthlyexpenses.server.domain.enums.Month;

import static java.util.Objects.isNull;

public class Expense {

    private Long id;
    private String name;
    private float price;
    private boolean paid;
    private User user;
    private ExpenseType expenseType;
    private Month month;
    private Year year;

    public Expense(String name, float price, boolean paid, User user, ExpenseType expenseType, Month month, Year year) {
        this.setName(name);
        this.setPrice(price);
        this.setPaid(paid);
        this.setUser(user);
        this.setExpenseType(expenseType);
        this.setMonth(month);
        this.setYear(year);
    }

    public Expense(Long id, String name, float price, boolean paid, User user, ExpenseType expenseType, Month month, Year year) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
        this.setPaid(paid);
        this.setUser(user);
        this.setExpenseType(expenseType);
        this.setMonth(month);
        this.setYear(year);
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
        validateName(name);
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

    private void validateName(String name) {
        if (isNull(name) || name.isEmpty()) {
            throw new InvalidArgumentException("O nome da despensa não pode estar em branco.");
        }
    }
}
