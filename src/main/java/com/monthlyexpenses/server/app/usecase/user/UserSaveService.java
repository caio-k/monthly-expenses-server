package com.monthlyexpenses.server.app.usecase.user;

import com.monthlyexpenses.server.app.exception.ResourceAlreadyExists;
import com.monthlyexpenses.server.app.repository.UserRepository;
import com.monthlyexpenses.server.domain.User;

import static java.lang.String.format;

public class UserSaveService {

    private final UserRepository userRepository;

    public UserSaveService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email, String password) {
        User user = new User(username, email, password);

        validateUsernameExistence(user.getUsername());
        validateEmailExistence(user.getEmail());

        userRepository.save(user);

        return user;
    }

    private void validateUsernameExistence(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new ResourceAlreadyExists(format("O username %s já está em uso.", username));
        }
    }

    private void validateEmailExistence(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExists(format("O email %s já está em uso.", email));
        }
    }
}
