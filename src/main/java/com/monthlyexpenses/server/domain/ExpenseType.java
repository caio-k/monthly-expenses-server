package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.model.User;

public class ExpenseType {

    private String name;
    private User user;

    public ExpenseType(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
