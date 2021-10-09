package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.app.exception.InvalidArgumentException;
import com.monthlyexpenses.server.domain.enums.Month;

import static java.util.Objects.isNull;

public class Revenue {

    private Long id;
    private User user;
    private Float value;
    private Month month;
    private Year year;

    public Revenue(User user, Float value, Month month, Year year) {
        this.setUser(user);
        this.setValue(value);
        this.setMonth(month);
        this.setYear(year);
    }

    public Revenue(Long id, User user, Float value, Month month, Year year) {
        this.setId(id);
        this.setUser(user);
        this.setValue(value);
        this.setMonth(month);
        this.setYear(year);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        validateValue(value);
        this.value = value;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    private void validateValue(Float value) {
        if (isNull(value) || value.toString().isEmpty()) {
            throw new InvalidArgumentException("O valor de receita deve estar preenchido.");
        }
    }
}
