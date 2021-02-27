package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expense_type", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class ExpenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "expenseType", cascade = {CascadeType.ALL})
    private Set<Expense> expenses = new HashSet<>();

    public ExpenseType(@NotBlank String name, User user) {
        this.name = name;
        this.user = user;
    }
}
