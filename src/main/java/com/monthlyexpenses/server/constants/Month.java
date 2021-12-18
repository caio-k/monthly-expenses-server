package com.monthlyexpenses.server.constants;

import com.monthlyexpenses.server.exceptions.InvalidArgumentException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.util.Arrays.stream;

@Getter
@AllArgsConstructor
public enum Month {

    JANUARY("Janeiro", 0),
    FEBRUARY("Fevereiro", 1),
    MARCH("Março", 2),
    APRIL("Abril", 3),
    MAY("Maio", 4),
    JUNE("Junho", 5),
    JULY("Julho", 6),
    AUGUST("Agosto", 7),
    SEPTEMBER("Setembro", 8),
    OCTOBER("Outubro", 9),
    NOVEMBER("Novembro", 10),
    DECEMBER("Dezembro", 11);

    private final String name;
    private final Integer number;

    public static Month findByMonthNumber(Integer number) {
        return stream(values())
                .filter(e -> e.getNumber().equals(number))
                .findFirst()
                .orElseThrow(() -> new InvalidArgumentException("O mês informado deve estar entre Janeiro e Dezembro."));
    }
}
