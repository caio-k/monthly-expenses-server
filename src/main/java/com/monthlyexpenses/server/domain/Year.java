package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.app.exception.InvalidArgumentException;

import static java.util.Objects.isNull;

public class Year {

    private Long id;
    private Integer number;
    private User user;

    public Year(Integer number, User user) {
        this.setNumber(number);
        this.setUser(user);
    }

    public Year(Long id, Integer number, User user) {
        this.setId(id);
        this.setNumber(number);
        this.setUser(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer yearNumber) {
        validateYearNumber(yearNumber);
        this.number = yearNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void validateYearNumber(Integer number) {
        if (isNull(number) || number.toString().length() != 4) {
            throw new InvalidArgumentException("O ano deve ser composto por 4 dígitos.");
        }
    }
}
