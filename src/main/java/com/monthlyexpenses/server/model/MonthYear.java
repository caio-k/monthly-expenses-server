package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
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

    public MonthYear(Month month, Year year) {
        this.id = new MonthYearKey(month.getId(), year.getId());
        this.month = month;
        this.year = year;
    }
}
