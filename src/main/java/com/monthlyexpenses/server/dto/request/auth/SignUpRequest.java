package com.monthlyexpenses.server.dto.request.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {

    @Size(min = 3, max = 20, message = "{username.size}")
    private String username;

    @NotBlank(message = "{email.not.blank}")
    @Size(max = 50, message = "{email.size}")
    @Email(message = "{email.not.valid}")
    private String email;

    @Size(min = 6, max = 40, message = "{password.size}")
    private String password;
}
