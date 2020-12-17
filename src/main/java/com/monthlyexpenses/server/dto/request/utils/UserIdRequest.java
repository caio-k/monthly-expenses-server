package com.monthlyexpenses.server.dto.request.utils;

import javax.validation.constraints.NotNull;

public class UserIdRequest {

    @NotNull(message = "{userId.not.null}")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
