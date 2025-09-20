package com.br.cloudnotes.adapters.out.persistence.User;

import com.br.cloudnotes.core.domain.exceptions.PageNotFoundException;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
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

    public List<UserEntity> getAllUsers(int page) throws Exception {
        int limit = 15;

        if (page < 1) {
            throw new Exception("INVALID_NUMBER_OF_PAGES");
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
                throw new PageNotFoundException(page);
            }

            Page<UserEntity> scannedPage = iterator.next();
            exclusiveStartKey = scannedPage.lastEvaluatedKey();

            if (exclusiveStartKey == null) {
                // reached the last page before requested
                throw new PageNotFoundException(page);
            }
        }

        // Now fetch the actual requested page
        ScanEnhancedRequest pageRequest = ScanEnhancedRequest.builder()
                .limit(limit)
                .exclusiveStartKey(exclusiveStartKey)
                .build();

        Iterator<Page<UserEntity>> iterator = userTable.scan(pageRequest).iterator();

        if (!iterator.hasNext()) {
            throw new PageNotFoundException(page);
        }

        return iterator.next().items().stream().toList();
    }

    public UserEntity getUserById(String id, String email) {
        return userTable.getItem(Key.builder().partitionValue(id).sortValue(email).build());
    }
}
