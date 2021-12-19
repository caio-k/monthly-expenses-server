package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final MessagesComponent messages;

    public Customer getCustomerByCustomerId(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("USER_NOT_FOUND")));
    }
}
