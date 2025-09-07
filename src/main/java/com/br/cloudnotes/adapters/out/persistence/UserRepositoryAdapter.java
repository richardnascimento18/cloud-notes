package com.br.cloudnotes.adapters.out.persistence;

import com.br.cloudnotes.core.model.User;
import com.br.cloudnotes.core.ports.out.UserRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final DynamoDbUserRepository dynamoDbUserRepository;

    public UserRepositoryAdapter(DynamoDbUserRepository dynamoDbUserRepository) {
        this.dynamoDbUserRepository = dynamoDbUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getEmail());
        UserEntity savedUserEntity = dynamoDbUserRepository.save(userEntity);
        return new User(savedUserEntity.getUserId(), savedUserEntity.getUserName(), savedUserEntity.getUserEmail());
    }

    @Override
    public boolean existsByEmail(String email) {
        return dynamoDbUserRepository.existsByEmail(email);
    }
}
