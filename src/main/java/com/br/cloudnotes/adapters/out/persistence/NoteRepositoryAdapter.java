package com.br.cloudnotes.adapters.out.persistence;

import com.br.cloudnotes.core.model.Note;
import com.br.cloudnotes.core.ports.out.NoteRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class NoteRepositoryAdapter implements NoteRepositoryPort {
    private final DynamoDbNoteRepository dynamoDbNoteRepository;

    public NoteRepositoryAdapter(DynamoDbNoteRepository dynamoDbNoteRepository) {
        this.dynamoDbNoteRepository = dynamoDbNoteRepository;
    }

    @Override
    public Note save(Note note){
        NoteEntity noteEntity = new NoteEntity(note.getUserId(), note.getId(), note.getTitle(), note.getContent());
        NoteEntity savedNoteEntity = dynamoDbNoteRepository.save(noteEntity);

        return new Note(savedNoteEntity.getUserId(), savedNoteEntity.getId(), savedNoteEntity.getTitle(), savedNoteEntity.getContent());
    }
}
