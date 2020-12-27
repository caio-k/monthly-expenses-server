package com.monthlyexpenses.server.dto.request.year;

import javax.validation.constraints.Min;

public class YearPostRequest {

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
    }
}
