package com.monthlyexpenses.server.dto.response.year;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YearResponse {

    private Long id;
    private Integer yearNumber;
}
