package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessagesComponent messages;

    public Customer getUserByUserId(Long customerId) {
        return userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("USER_NOT_FOUND")));
    }
}
