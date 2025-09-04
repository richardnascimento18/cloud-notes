package com.br.cloudnotes.core.ports.out;

import com.br.cloudnotes.core.model.User;

public interface UserRepositoryPort {
    User save(User user);
}
