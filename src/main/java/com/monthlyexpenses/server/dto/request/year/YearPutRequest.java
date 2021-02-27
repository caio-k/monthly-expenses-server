package com.monthlyexpenses.server.dto.request.year;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class YearPutRequest {

    @Min(value = 0, message = "{yearNumber.min.zero}")
    private Integer yearNumber;

    @NotNull(message = "{yearId.not.null}")
    private Long yearId;
}
