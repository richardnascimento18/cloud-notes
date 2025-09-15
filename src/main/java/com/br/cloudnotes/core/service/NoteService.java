package com.br.cloudnotes.core.service;

import com.br.cloudnotes.core.model.Note;
import com.br.cloudnotes.core.ports.in.NoteUseCases;
import com.br.cloudnotes.core.ports.out.NoteRepositoryPort;

import java.util.UUID;

public class NoteService implements NoteUseCases {
    private final NoteRepositoryPort noteRepositoryPort;

    public NoteService(NoteRepositoryPort noteRepositoryPort) {
        this.noteRepositoryPort = noteRepositoryPort;
    }

    @Override
    public Note createNote(String userId, String title, String content) {
        return noteRepositoryPort.save(new Note(userId, null, title, content));
    }
}
