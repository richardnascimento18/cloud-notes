package com.br.cloudnotes.core.service;

import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.in.CreateUserUseCase;
import com.br.cloudnotes.core.ports.out.UserRepositoryPort;

public class UserService implements CreateUserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User createUser(String name, String email) {
        User user = new User(null, name, email);
        return userRepositoryPort.save(user);
    }
}
