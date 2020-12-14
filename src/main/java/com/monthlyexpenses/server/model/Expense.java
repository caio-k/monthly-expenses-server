package com.monthlyexpenses.server.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    @NotBlank
    private float price;

    @NotBlank
    private boolean paid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "month_year_id")
    private MonthYear monthYear;

    public Expense() {

    }

    public Expense(@NotBlank String name, @Min(0) @NotBlank float price, @NotBlank boolean paid,
                   ExpenseType expenseType, User user, MonthYear monthYear) {
        this.name = name;
        this.price = price;
        this.paid = paid;
        this.expenseType = expenseType;
        this.user = user;
        this.monthYear = monthYear;
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

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MonthYear getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(MonthYear monthYear) {
        this.monthYear = monthYear;
    }
}
