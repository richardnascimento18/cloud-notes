package com.br.cloudnotes.core.ports.out;

import com.br.cloudnotes.core.model.Note;

public interface NoteRepositoryPort {
    Note save(Note note);
}
