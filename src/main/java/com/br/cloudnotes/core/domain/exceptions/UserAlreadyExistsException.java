package com.br.cloudnotes.core.domain.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("USER_" + email + "_ALREADY_EXISTS");
    }
}
