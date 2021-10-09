package com.monthlyexpenses.server.app.repository;

import com.monthlyexpenses.server.domain.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findUserById(Long id);
}
