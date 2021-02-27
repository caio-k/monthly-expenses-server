package com.monthlyexpenses.server.dto.request.year;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class YearPostRequest {

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;
}
