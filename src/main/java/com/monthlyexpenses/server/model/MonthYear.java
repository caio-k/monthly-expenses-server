package com.monthlyexpenses.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "month_years", uniqueConstraints = @UniqueConstraint(columnNames = {"month", "year", "user_id"}))
public class MonthYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private int month;

    @NotBlank
    private int year;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "monthYear")
    private Set<InitialMoney> initialMoneySet = new HashSet<>();

    @OneToMany(mappedBy = "monthYear")
    private Set<Expense> expenses = new HashSet<>();

    public MonthYear() {

    }

    public MonthYear(@NotBlank int month, @NotBlank int year, User user) {
        this.month = month;
        this.year = year;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<InitialMoney> getInitialMoneySet() {
        return initialMoneySet;
    }

    public void setInitialMoneySet(Set<InitialMoney> initialMoneySet) {
        this.initialMoneySet = initialMoneySet;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }
}
