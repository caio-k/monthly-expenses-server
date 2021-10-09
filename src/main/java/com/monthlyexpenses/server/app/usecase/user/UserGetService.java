package com.monthlyexpenses.server.app.usecase.user;

import com.monthlyexpenses.server.app.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.app.repository.UserRepository;
import com.monthlyexpenses.server.domain.User;

import static java.lang.String.format;

public class UserGetService {

    private final UserRepository userRepository;

    public UserGetService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByIdOrElseThrow(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Usuário não encontrado a partir do identificador %d", id)));
    }
}
