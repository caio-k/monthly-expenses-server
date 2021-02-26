package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final MessagesComponent messages;

    @Autowired
    public UserService(UserRepository userRepository, MessagesComponent messages) {
        this.userRepository = userRepository;
        this.messages = messages;
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("USER_NOT_FOUND")));
    }
}
