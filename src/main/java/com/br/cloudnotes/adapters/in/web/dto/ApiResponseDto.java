package com.br.cloudnotes.adapters.in.web.dto;

import java.time.Instant;
import java.util.Map;

public class ApiResponseDto<T> {

    private final int status;
    private final String version;
    private final Instant timestamp;
    private final T body;
    private final Map<String, Link> _links;

    public ApiResponseDto(int status,String version, T body, Map<String, Link> links) {
        this.status = status;
        this.version = version;
        this.timestamp = Instant.now();
        this.body = body;
        this._links = links;
    }

    public int getStatus() { return status; }
    public String getVersion() { return version; }
    public Instant getTimestamp() { return timestamp; }
    public T getBody() { return body; }
    public Map<String, Link> get_links() { return _links; }

    public static class Link {
        private final String method;
        private final String href;
        private final String rel;

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
