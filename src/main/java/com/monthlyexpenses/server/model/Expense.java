package com.monthlyexpenses.server.model;

import com.monthlyexpenses.server.constants.Month;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {

    @Id
    @Column(name = "IDT_EXPENSE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenseSequence")
    @SequenceGenerator(name = "expenseSequence", sequenceName = "SQ_EXPENSE_IDT", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "NAM_EXPENSE")
    private String name;

    @Column(name = "NUM_PRICE")
    private float price;

    @Column(name = "FLG_PAID")
    private boolean paid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDT_EXPENSE_TYPE")
    private ExpenseType expenseType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDT_CUSTOMER")
    private Customer customer;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_YEAR")
    private Year year;

    @Enumerated(EnumType.STRING)
    @Column(name = "COD_MONTH")
    private Month month;

    public Expense(String name, float price, boolean paid,
                   ExpenseType expenseType, Customer customer, Year year, Month month) {
        this.name = name;
        this.price = price;
        this.paid = paid;
        this.expenseType = expenseType;
        this.customer = customer;
        this.year = year;
        this.month = month;
    }
}
