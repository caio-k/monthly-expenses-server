package com.monthlyexpenses.server.dto.response.year;

public class YearResponse {

    private Long id;
    private Integer yearNumber;

    public YearResponse(Long id, Integer yearNumber) {
        this.id = id;
        this.yearNumber = yearNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
    }
}
