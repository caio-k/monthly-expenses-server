package com.monthlyexpenses.server.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "{username.not.blank}")
    private String username;

    @NotBlank(message = "{password.not.blank}")
    private String password;
}
