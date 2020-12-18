package com.monthlyexpenses.server.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "month_years")
public class MonthYear implements Serializable {

    @EmbeddedId
    private MonthYearKey id;

    @ManyToOne
    @MapsId("monthId")
    @JoinColumn(name = "month_id")
    private Month month;

    @ManyToOne
    @MapsId("yearId")
    @JoinColumn(name = "year_id")
    private Year year;

    @OneToMany(mappedBy = "monthYear", cascade = CascadeType.ALL)
    private Set<InitialMoney> initialMoneySet = new HashSet<>();

    @OneToMany(mappedBy = "monthYear", cascade = CascadeType.ALL)
    private Set<Expense> expenses = new HashSet<>();

    public MonthYear() {

    }

    public MonthYear(Month month, Year year) {
        this.id = new MonthYearKey(month.getId(), year.getId());
        this.month = month;
        this.year = year;
    }

    public MonthYearKey getId() {
        return id;
    }

    public void setId(MonthYearKey id) {
        this.id = id;
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
