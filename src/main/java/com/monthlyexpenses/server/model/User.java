package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username", "email"})
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Year> years = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ExpenseType> expenseTypes = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<InitialMoney> initialMoneySet = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Expense> expenses = new HashSet<>();

    public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
                @NotBlank @Size(max = 120) String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
