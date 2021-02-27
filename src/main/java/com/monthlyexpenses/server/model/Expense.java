package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    private float price;

    @NotNull
    private boolean paid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "monthId"),
            @JoinColumn(name = "yearId")
    })
    private MonthYear monthYear;

    public Expense(@NotBlank String name, @Min(0) float price, @NotNull boolean paid,
                   ExpenseType expenseType, User user, MonthYear monthYear) {
        this.name = name;
        this.price = price;
        this.paid = paid;
        this.expenseType = expenseType;
        this.user = user;
        this.monthYear = monthYear;
    }
}
