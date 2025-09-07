package com.br.cloudnotes.adapters.out.persistence;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.UUID;

@Repository
public class DynamoDbUserRepository {

    private final DynamoDbTable<UserEntity> userTable;

    public DynamoDbUserRepository(DynamoDbEnhancedClient enhancedClient) {
        this.userTable = enhancedClient.table("tb_users", TableSchema.fromBean(UserEntity.class));
    }

    public UserEntity save(UserEntity user) {
        if (user.getUserId() == null) {
            user.setUserId(UUID.randomUUID());
        }
        userTable.putItem(user);
        return user;
    }

    public boolean existsByEmail(String email) {
        return userTable.scan()
                .items()
                .stream()
                .anyMatch(u -> u.getUserEmail().equals(email));
    }
}
