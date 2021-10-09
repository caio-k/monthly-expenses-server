package com.monthlyexpenses.server.domain;

import com.monthlyexpenses.server.app.exception.InvalidArgumentException;

import static java.util.Objects.isNull;

public class User {

    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        validateUsername(username);
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    private void validateUsername(String username) {
        if (isNull(username) || username.length() < 3 || username.length() > 20) {
            throw new InvalidArgumentException("O username deve ter de 3 a 20 caracteres.");
        }
    }

    private void validateEmail(String email) {
        if (isNull(email) || email.isEmpty()) {
            throw new InvalidArgumentException("O email não pode estar vazio.");
        }
    }

    private void validatePassword(String password) {
        if (isNull(password) || password.length() < 6) {
            throw new InvalidArgumentException("A senha deve ter no mínimo 6 caracteres.");
        }
    }
}
