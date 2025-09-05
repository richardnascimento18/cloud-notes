package com.br.cloudnotes.adapters.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequestDto(
        @NotBlank(message = "NAME_IS_REQUIRED")
        @Pattern(regexp = "^[A-Z][a-z]+$", message = "ONLY_FIRST_NAME_WITH_FIRST_LETTER_UPPERCASE")
        String name,

        @NotBlank(message = "EMAIL_IS_REQUIRED")
        @Email(message = "EMAIL_MUST_BE_A_VALID_EMAIL")
        String email
) {}
