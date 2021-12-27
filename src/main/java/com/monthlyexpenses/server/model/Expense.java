package com.monthlyexpenses.server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {

    @Id
    @Column(name = "IDT_EXPENSE")
    @GeneratedValue(strategy = SEQUENCE, generator = "expenseSequence")
    @SequenceGenerator(name = "expenseSequence", sequenceName = "SQ_EXPENSE_IDT", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "NAM_EXPENSE")
    private String name;

    @Column(name = "NUM_PRICE")
    private float price;

    @Column(name = "FLG_PAID")
    private boolean paid;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_EXPENSE_TYPE")
    private ExpenseType expenseType;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_CUSTOMER")
    private Customer customer;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_YEAR")
    private Year year;

    @Column(name = "COD_MONTH")
    private Integer monthNumber;
}
