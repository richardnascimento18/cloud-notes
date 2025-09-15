package com.br.cloudnotes.adapters.out.persistence;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

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


}
