package com.br.cloudnotes.core.ports.out;

import com.br.cloudnotes.core.model.Note;

import java.util.List;

public interface NoteRepositoryPort {
    Note save(Note note);
    List<Note> getAllNotes(String userId, int page) throws Exception;
}
