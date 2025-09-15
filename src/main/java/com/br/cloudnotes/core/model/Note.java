package com.br.cloudnotes.core.model;

import java.util.UUID;

public class Note {
    private String userId;
    private UUID id;
    private String title;
    private String content;

    public Note(String userId, UUID id, String title, String content) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
