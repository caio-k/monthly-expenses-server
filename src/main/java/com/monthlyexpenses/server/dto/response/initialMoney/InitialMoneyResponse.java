package com.monthlyexpenses.server.dto.response.initialMoney;

public class InitialMoneyResponse {

    private Long initialMoneyId;

    private Integer month;

    private Integer year;

    private float initialMoney;

    public InitialMoneyResponse(Long initialMoneyId, Integer month, Integer year, float initialMoney) {
        this.initialMoneyId = initialMoneyId;
        this.month = month;
        this.year = year;
        this.initialMoney = initialMoney;
    }

    public Long getInitialMoneyId() {
        return initialMoneyId;
    }

    public void setInitialMoneyId(Long initialMoneyId) {
        this.initialMoneyId = initialMoneyId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public float getInitialMoney() {
        return initialMoney;
    }

    public void setInitialMoney(float initialMoney) {
        this.initialMoney = initialMoney;
    }
}
