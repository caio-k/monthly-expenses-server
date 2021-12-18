package com.monthlyexpenses.server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Year {

    @Id
    @Column(name = "IDT_YEAR")
    @GeneratedValue(strategy = SEQUENCE, generator = "yearSequence")
    @SequenceGenerator(name = "yearSequence", sequenceName = "SQ_YEAR_IDT", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "NUM_YEAR")
    private Integer yearNumber;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "IDT_CUSTOMER")
    private Customer customer;
}
