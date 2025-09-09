package com.br.cloudnotes.core.service;

import com.br.cloudnotes.core.domain.exceptions.UserAlreadyExistsException;
import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.in.UserUseCases;
import com.br.cloudnotes.core.ports.out.UserRepositoryPort;

import java.util.List;

public class UserService implements UserUseCases {
    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User createUser(String name, String email) {
        if (userRepositoryPort.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }
        return userRepositoryPort.save(new User(null, name, email));
    }

    @Override
    public List<User> getAllUsers(int page) throws Exception {
        return userRepositoryPort.getAllUsers(page);
    }
}
