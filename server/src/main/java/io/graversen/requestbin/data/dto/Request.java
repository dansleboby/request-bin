package io.graversen.requestbin.data.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Request {
    private final String binId;
    private final LocalDateTime createdAt;
    private final String encodedRequestBody;
    private final String queryParameters;
    private final String httpHeaders;
    private final String ipAddress;
    private final String httpVerb;
    private final String requestDuration;
}
