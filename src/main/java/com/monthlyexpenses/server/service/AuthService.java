package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.request.auth.LoginRequest;
import com.monthlyexpenses.server.dto.request.auth.SignUpRequest;
import com.monthlyexpenses.server.dto.response.auth.JwtResponse;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.error.exception.UniqueViolationException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.ERole;
import com.monthlyexpenses.server.model.Role;
import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.repository.RoleRepository;
import com.monthlyexpenses.server.repository.UserRepository;
import com.monthlyexpenses.server.security.jwt.JwtUtils;
import com.monthlyexpenses.server.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final MessagesComponent messages;

    @Autowired
    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils, MessagesComponent messages) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.messages = messages;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public MessageResponse registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UniqueViolationException(messages.get("USERNAME_ALREADY_TAKEN"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UniqueViolationException(messages.get("EMAIL_ALREADY_TAKEN"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("ROLE_NOT_FOUND")));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse(messages.get("USER_REGISTERED_SUCCESSFULLY"));
    }
}
