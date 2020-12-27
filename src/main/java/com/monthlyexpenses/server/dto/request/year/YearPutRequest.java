package com.monthlyexpenses.server.dto.request.year;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class YearPutRequest {

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;

    @NotNull(message = "{yearId.not.null}")
    private Long yearId;

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }
}
