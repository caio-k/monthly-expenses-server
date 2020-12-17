package com.monthlyexpenses.server.dto.request.year;

import javax.validation.constraints.NotNull;

public class YearDeleteRequest {

    @NotNull(message = "{yearId.not.null}")
    private Long yearId;

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long yearId) {
        this.yearId = yearId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
