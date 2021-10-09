package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.app.exception.InvalidArgumentException;

import static java.util.Objects.isNull;

public class ExpenseType {

    private Long id;
    private String name;
    private User user;

    public ExpenseType(String name, User user) {
        this.setName(name);
        this.setUser(user);
    }

    public ExpenseType(Long id, String name, User user) {
        this.setId(id);
        this.setName(name);
        this.setUser(user);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void validateName(String name) {
        if (isNull(name) || name.isEmpty()) {
            throw new InvalidArgumentException("O nome do tipo de despesa não pode estar em branco.");
        }
    }
}
