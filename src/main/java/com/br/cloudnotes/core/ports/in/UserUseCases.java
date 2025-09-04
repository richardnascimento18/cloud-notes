package com.br.cloudnotes.core.ports.in;

import com.br.cloudnotes.core.model.User;

public interface UserUseCases {
    User createUser(String name, String email);
}
