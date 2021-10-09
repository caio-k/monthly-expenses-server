package com.monthlyexpenses.server.domain;

public class Year {

    private Integer number;
    private User user;

    public Year(Integer number, User user) {
        this.number = number;
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer yearNumber) {
        this.number = yearNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
