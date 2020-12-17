package com.monthlyexpenses.server.dto.request.year;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class YearPostRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
    }
}
