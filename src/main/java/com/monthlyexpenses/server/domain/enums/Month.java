package com.monthlyexpenses.server.domain.enums;

public enum Month {

    JANUARY("Janeiro", 1),
    FEBRUARY("Fevereiro", 2),
    MARCH("Março", 3),
    APRIL("Abril", 4),
    MAY("Maio", 5),
    JUNE("Junho", 6),
    JULY("Julho", 7),
    AUGUST("Agosto", 8),
    SEPTEMBER("Setembro", 9),
    OCTOBER("Outubro", 10),
    NOVEMBER("Novembro", 11),
    DECEMBER("Dezembro", 12);

    private final String name;
    private final Integer number;

    Month(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }
}
