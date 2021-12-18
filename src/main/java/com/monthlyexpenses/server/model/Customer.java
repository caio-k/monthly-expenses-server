package com.monthlyexpenses.server.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @Column(name = "IDT_CUSTOMER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSequence")
    @SequenceGenerator(name = "customerSequence", sequenceName = "SQ_CUSTOMER_IDT", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "NAM_USERNAME")
    private String username;

    @NotBlank
    @Column(name = "NAM_EMAIL")
    private String email;

    @NotBlank
    @Column(name = "DES_PASSWORD")
    private String password;

    public Customer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
