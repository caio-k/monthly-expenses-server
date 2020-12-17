package com.monthlyexpenses.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MonthYearKey implements Serializable {

    @Column(name = "month_id")
    private Long monthId;

    @Column(name = "year_id")
    private Long yearId;

    public MonthYearKey() {

    }

    public MonthYearKey(Long monthId, Long yearId) {
        this.monthId = monthId;
        this.yearId = yearId;
    }

    public Long getMonthId() {
        return monthId;
    }

    public void setMonthId(Long monthId) {
        this.monthId = monthId;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }
}
