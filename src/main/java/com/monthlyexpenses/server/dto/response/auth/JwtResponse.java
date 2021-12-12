package com.monthlyexpenses.server.dto.response.auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {

    private String accessToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private final String tokenType = "Bearer";
}
