package com.br.cloudnotes.core.ports.out;

import com.br.cloudnotes.core.model.User;

import java.util.List;

public interface UserRepositoryPort {
    User save(User user);
    boolean existsByEmail(String email);
    List<User> getAllUsers(int page);
}
