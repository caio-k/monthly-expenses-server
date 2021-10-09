package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.domain.enums.Month;

public class Revenue {

    private User user;
    private Float value;
    private Month month;
    private Year year;

    public Revenue(User user, Float value, Month month, Year year) {
        this.user = user;
        this.value = value;
        this.month = month;
        this.year = year;
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
}
