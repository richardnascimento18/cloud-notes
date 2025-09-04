package com.br.cloudnotes.core.ports.in;

import com.br.cloudnotes.core.model.User;

public interface CreateUserUseCase {
    User createUser(String name, String email);
}
