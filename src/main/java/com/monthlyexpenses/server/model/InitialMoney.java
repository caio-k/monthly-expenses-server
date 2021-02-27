package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "initial_money", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"monthId", "yearId", "user_id"})
})
public class InitialMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private float initialMoney;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "monthId"),
            @JoinColumn(name = "yearId")
    })
    private MonthYear monthYear;

    public InitialMoney(@NotBlank float initialMoney, User user, MonthYear monthYear) {
        this.initialMoney = initialMoney;
        this.user = user;
        this.monthYear = monthYear;
    }
}
