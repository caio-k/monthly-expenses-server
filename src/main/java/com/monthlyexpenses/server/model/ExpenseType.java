package com.monthlyexpenses.server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExpenseType {

    @Id
    @Column(name = "IDT_EXPENSE_TYPE")
    @GeneratedValue(strategy = SEQUENCE, generator = "expenseTypeSequence")
    @SequenceGenerator(name = "expenseTypeSequence", sequenceName = "SQ_EXPENSE_TYPE_IDT", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "NAM_EXPENSE_TYPE")
    private String name;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_CUSTOMER")
    private Customer customer;
}
