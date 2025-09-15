package com.br.cloudnotes.core.ports.in;

import com.br.cloudnotes.core.model.Note;

public interface NoteUseCases {
    Note createNote (String userId, String title, String content);
}
