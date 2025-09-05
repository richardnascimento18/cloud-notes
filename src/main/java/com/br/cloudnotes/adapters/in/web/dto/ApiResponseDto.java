package com.br.cloudnotes.adapters.in.web.dto;

import java.time.Instant;
import java.util.Map;

public class ApiResponseDto<T> {
    private int status;
    private String apiVersion = "v0.1.0";
    private Instant timestamp;
    private T body;
    private Map<String, Link> _links;

    public ApiResponseDto(int status, T body, Map<String, Link> links) {
        this.status = status;
        this.timestamp = Instant.now();
        this.body = body;
        this._links = links;
    }

    // getters
    public int getStatus() { return status; }
    public String getApiVersion() { return apiVersion; }
    public Instant getTimestamp() { return timestamp; }
    public T getBody() { return body; }
    public Map<String, Link> get_links() { return _links; }

    public static class Link {
        private String method;
        private String href;
        private String rel;

        public Link(String method, String href, String rel) {
            this.method = method;
            this.href = href;
            this.rel = rel;
        }

        public String getMethod() { return method; }
        public String getHref() { return href; }
        public String getRel() { return rel; }
    }
}
