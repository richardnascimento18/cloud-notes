package com.br.cloudnotes.adapters.out.persistence;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.UUID;

@DynamoDbBean
public class NoteEntity {
    private String userId;
    private UUID id;
    private String title;
    private String content;

    public NoteEntity() { }
    public NoteEntity(String userId, UUID id, String title, String content) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @DynamoDbPartitionKey
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDbSortKey
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
