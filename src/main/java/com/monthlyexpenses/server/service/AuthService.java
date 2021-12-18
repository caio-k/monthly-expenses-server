package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.request.auth.LoginRequest;
import com.monthlyexpenses.server.dto.request.auth.SignUpRequest;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.auth.JwtResponse;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.repository.UserRepository;
import com.monthlyexpenses.server.security.jwt.JwtUtils;
import com.monthlyexpenses.server.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final MessagesComponent messages;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public JwtResponse authenticateCustomer(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JwtResponse.builder()
                .accessToken(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }

    public MessageResponse registerCustomer(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UniqueViolationException(messages.get("USERNAME_ALREADY_TAKEN"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UniqueViolationException(messages.get("EMAIL_ALREADY_TAKEN"));
        }

        Customer customer = Customer.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();

        userRepository.saveAndFlush(customer);

        return MessageResponse.builder().message(messages.get("USER_REGISTERED_SUCCESSFULLY")).build();
    }
}
