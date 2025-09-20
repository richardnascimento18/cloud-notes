package com.br.cloudnotes.core.ports.in;

import com.br.cloudnotes.core.model.Note;

import java.util.List;

public interface NoteUseCases {
    Note createNote (String userId, String title, String content);
    List<Note> getAllNotes(String userId, int page) throws Exception;
}
