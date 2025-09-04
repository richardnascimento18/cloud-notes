package com.br.cloudnotes.adapters.out.persistence;

import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository springDataUserRepository;

    public UserRepositoryAdapter(SpringDataUserRepository springDataUserRepository) {
        this.springDataUserRepository = springDataUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getEmail());
        UserEntity savedUserEntity = springDataUserRepository.save(userEntity);
        return new User(savedUserEntity.getId(), savedUserEntity.getName(), savedUserEntity.getEmail());
    }
}
