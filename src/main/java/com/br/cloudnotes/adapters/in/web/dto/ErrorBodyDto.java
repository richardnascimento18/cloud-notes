package com.br.cloudnotes.adapters.in.web.dto;

public class ErrorBodyDto {
    private String code;
    private String error;

    public ErrorBodyDto(String code, String error) {
        this.code = code;
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}

