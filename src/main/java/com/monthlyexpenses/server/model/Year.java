package com.monthlyexpenses.server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Year {

    @Id
    @Column(name = "IDT_YEAR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "yearSequence")
    @SequenceGenerator(name = "yearSequence", sequenceName = "SQ_YEAR_IDT", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "NUM_YEAR")
    private Integer yearNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDT_CUSTOMER")
    private Customer customer;

    public Year(Integer yearNumber, Customer customer) {
        this.yearNumber = yearNumber;
        this.customer = customer;
    }
}
