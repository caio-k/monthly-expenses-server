package com.monthlyexpenses.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "months", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"month_number"})
})
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "month_number")
    private Integer monthNumber;

    @OneToMany(mappedBy = "month")
    private Set<MonthYear> monthYearSet = new HashSet<>();

    public Month() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public Set<MonthYear> getMonthYearSet() {
        return monthYearSet;
    }

    public void setMonthYearSet(Set<MonthYear> monthYearSet) {
        this.monthYearSet = monthYearSet;
    }
}
