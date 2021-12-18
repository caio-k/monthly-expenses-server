package com.monthlyexpenses.server.model;

import com.monthlyexpenses.server.constants.Month;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InitialMoney {

    @Id
    @Column(name = "IDT_INITIAL_MONEY")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "initialMoneySequence")
    @SequenceGenerator(name = "initialMoneySequence", sequenceName = "SQ_INITIAL_MONEY_IDT", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "NUM_INITIAL_MONEY")
    private float initialMoney;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_CUSTOMER")
    private Customer customer;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_YEAR")
    private Year year;

    @Enumerated(EnumType.STRING)
    @Column(name = "COD_MONTH")
    private Month month;

    public InitialMoney(float initialMoney, Customer customer, Year year, Month month) {
        this.initialMoney = initialMoney;
        this.customer = customer;
        this.year = year;
        this.month = month;
    }
}
