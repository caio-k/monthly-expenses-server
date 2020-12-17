package com.monthlyexpenses.server.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "initial_money", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"monthId", "yearId", "user_id"})
})
public class InitialMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private float initialMoney;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "monthId", insertable = false, updatable = false),
            @JoinColumn(name = "yearId", insertable = false, updatable = false)
    })
    private MonthYear monthYear;

    public InitialMoney() {

    }

    public InitialMoney(@NotBlank float initialMoney, User user, MonthYear monthYear) {
        this.initialMoney = initialMoney;
        this.user = user;
        this.monthYear = monthYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(float initialMoney) {
        this.initialMoney = initialMoney;
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
