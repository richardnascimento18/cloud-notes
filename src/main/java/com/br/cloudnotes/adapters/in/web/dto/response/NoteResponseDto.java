package com.br.cloudnotes.adapters.in.web.dto.response;

import java.util.UUID;

public class NoteResponseDto {
    private final UUID id;
    private final String userId;
    private final String title;
    private final String content;

    public NoteResponseDto(UUID id, String userId, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public UUID getId() { return id; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}
