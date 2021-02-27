package com.monthlyexpenses.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class MonthYearKey implements Serializable {

    @Column(name = "month_id")
    private Long monthId;

    @Column(name = "year_id")
    private Long yearId;

    public MonthYearKey(Long monthId, Long yearId) {
        this.monthId = monthId;
        this.yearId = yearId;
    }
}
