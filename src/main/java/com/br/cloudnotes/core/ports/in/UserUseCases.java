package com.br.cloudnotes.core.ports.in;

import com.br.cloudnotes.core.model.User;

import java.util.List;

public interface UserUseCases {
    User createUser(String name, String email);
    List<User> getAllUsers(int page) throws Exception;
    User getUserById(String id, String email) throws Exception;
}
