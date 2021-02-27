package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "years", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"year_number", "user_id"})
})
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "year_number")
    private Integer yearNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "year", cascade = {CascadeType.ALL})
    private Set<MonthYear> monthYearSet = new HashSet<>();

    public Year(@NotNull Integer yearNumber, User user) {
        this.yearNumber = yearNumber;
        this.user = user;
    }
}
