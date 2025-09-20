package com.br.cloudnotes.adapters.out.persistence.Note;

import com.br.cloudnotes.adapters.out.persistence.User.UserEntity;
import com.br.cloudnotes.core.domain.exceptions.PageNotFoundException;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class DynamoDbNoteRepository {
    private final DynamoDbTable<NoteEntity> noteTable;

    public DynamoDbNoteRepository(DynamoDbEnhancedClient enhancedClient) {
        this.noteTable = enhancedClient.table("tb_notes", TableSchema.fromBean(NoteEntity.class));
    }

    public NoteEntity save(NoteEntity note) {
        if (note.getId() == null) {
            note.setId(UUID.randomUUID());
        }
        noteTable.putItem(note);
        return note;
    }

    public List<NoteEntity> getAllNotes(String userId, int page) throws Exception {
        int limit = 15;

        if (page < 1) {
            throw new Exception("INVALID_NUMBER_OF_PAGES");
        }

        Map<String, AttributeValue> exclusiveStartKey = null;

        // Iterate until we reach the desired page
        for (int currentPage = 1; currentPage < page; currentPage++) {
            QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                    .queryConditional(
                            QueryConditional.keyEqualTo(Key.builder().partitionValue(userId).build())
                    )
                    .limit(limit)
                    .exclusiveStartKey(exclusiveStartKey)
                    .build();

            Iterator<Page<NoteEntity>> iterator = noteTable.query(request).iterator();

            if (!iterator.hasNext()) {
                throw new PageNotFoundException(page);
            }

            Page<NoteEntity> queriedPage = iterator.next();
            exclusiveStartKey = queriedPage.lastEvaluatedKey();

            if (exclusiveStartKey == null) {
                throw new PageNotFoundException(page);
            }
        }

        // Now fetch the requested page
        QueryEnhancedRequest pageRequest = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.keyEqualTo(Key.builder().partitionValue(userId).build())
                )
                .limit(limit)
                .exclusiveStartKey(exclusiveStartKey)
                .build();

        Iterator<Page<NoteEntity>> iterator = noteTable.query(pageRequest).iterator();

        if (!iterator.hasNext()) {
            throw new PageNotFoundException(page);
        }

        return iterator.next().items().stream().toList();
    }

}
