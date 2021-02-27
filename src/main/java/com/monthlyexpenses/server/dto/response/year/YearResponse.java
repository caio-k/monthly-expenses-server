package com.monthlyexpenses.server.dto.response.year;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YearResponse {

    private Long id;
    private Integer yearNumber;

    public YearResponse(Long id, Integer yearNumber) {
        this.id = id;
        this.yearNumber = yearNumber;
    }
}
