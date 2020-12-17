package com.monthlyexpenses.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "years", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"year_number", "user_id"})
})
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "year_number")
    private Integer yearNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "year", cascade = {CascadeType.ALL})
    private Set<MonthYear> monthYearSet = new HashSet<>();

    public Year() {

    }

    public Year(@NotNull Integer yearNumber, User user) {
        this.yearNumber = yearNumber;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MonthYear> getMonthYearSet() {
        return monthYearSet;
    }

    public void setMonthYearSet(Set<MonthYear> monthYearSet) {
        this.monthYearSet = monthYearSet;
    }
}
