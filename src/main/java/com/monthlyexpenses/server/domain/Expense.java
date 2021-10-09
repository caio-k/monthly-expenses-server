package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.app.exception.InvalidArgumentException;
import com.monthlyexpenses.server.domain.enums.Month;

import static java.util.Objects.isNull;

public class Expense {

    private Long id;
    private String name;
    private Float price;
    private Boolean paid;
    private User user;
    private ExpenseType expenseType;
    private Month month;
    private Year year;

    public Expense(String name, Float price, Boolean paid, User user, ExpenseType expenseType, Month month, Year year) {
        this.setName(name);
        this.setPrice(price);
        this.setPaid(paid);
        this.setUser(user);
        this.setExpenseType(expenseType);
        this.setMonth(month);
        this.setYear(year);
    }

    public Expense(Long id, String name, Float price, Boolean paid, User user, ExpenseType expenseType, Month month, Year year) {
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        validatePrice(price);
        this.price = price;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        validatePaid(paid);
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

    private void validatePrice(Float price) {
        if (isNull(price) || price.toString().isEmpty()) {
            throw new InvalidArgumentException("O valor de despesa deve estar preenchido.");
        }
    }

    private void validatePaid(Boolean paid) {
        if (isNull(paid)) {
            throw new InvalidArgumentException("A flag de já estar pago não pode estar vazia.");
        }
    }
}
