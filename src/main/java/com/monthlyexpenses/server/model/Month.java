package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "months", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"month_number"})
})
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    @Max(11)
    @Column(name = "month_number")
    private Integer monthNumber;

    @OneToMany(mappedBy = "month")
    private Set<MonthYear> monthYearSet = new HashSet<>();
}
