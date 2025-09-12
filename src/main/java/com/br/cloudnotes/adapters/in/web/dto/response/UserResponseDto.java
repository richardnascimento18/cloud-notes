package com.br.cloudnotes.adapters.in.web.dto.response;

import java.util.UUID;

public class UserResponseDto {
    private final UUID id;
    private final String name;
    private final String email;

    public UserResponseDto(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
