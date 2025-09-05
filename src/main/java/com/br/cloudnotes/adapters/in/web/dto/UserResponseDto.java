package com.br.cloudnotes.adapters.in.web.dto;

import java.util.UUID;

public class UserResponseDto {
    private UUID id;
    private String name;
    private String email;

    public UserResponseDto(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // getters only (immutable response)
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
