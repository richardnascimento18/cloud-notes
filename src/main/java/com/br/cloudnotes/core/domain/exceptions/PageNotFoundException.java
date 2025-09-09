package com.br.cloudnotes.core.domain.exceptions;

public class PageNotFoundException extends Exception {
    public PageNotFoundException(int page) {
        super("PAGE_" + page + "_NOT_FOUND");
    }
}
