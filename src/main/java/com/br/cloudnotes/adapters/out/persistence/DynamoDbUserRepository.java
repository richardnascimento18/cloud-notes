package com.br.cloudnotes.adapters.out.persistence;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    public List<UserEntity> getAllUsers(int page) {
        int limit = 15;

        if (page < 1) {
            return List.of(); // invalid page numbers return empty
        }

        Map<String, AttributeValue> exclusiveStartKey = null;

        // Iterate until we reach the desired page
        for (int currentPage = 1; currentPage < page; currentPage++) {
            ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                    .limit(limit)
                    .exclusiveStartKey(exclusiveStartKey)
                    .build();

            Iterator<Page<UserEntity>> iterator = userTable.scan(request).iterator();

            if (!iterator.hasNext()) {
                // no more pages
                return List.of();
            }

            Page<UserEntity> scannedPage = iterator.next();
            exclusiveStartKey = scannedPage.lastEvaluatedKey();

            if (exclusiveStartKey == null) {
                // reached the last page before requested
                return List.of();
            }
        }

        // Now fetch the actual requested page
        ScanEnhancedRequest pageRequest = ScanEnhancedRequest.builder()
                .limit(limit)
                .exclusiveStartKey(exclusiveStartKey)
                .build();

        Iterator<Page<UserEntity>> iterator = userTable.scan(pageRequest).iterator();

        if (!iterator.hasNext()) {
            return List.of();
        }

        return iterator.next().items().stream().toList();
    }
}
