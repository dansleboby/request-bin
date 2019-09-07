package io.graversen.requestbin.data.service;

import lombok.Value;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Value
public class CreateRequest {
    private final String binId;
    private final String requestBody;
    private final Map<String, String> queryParameters;
    private final Map<String, String> httpHeaders;
    private final String ipAddress;
    private final String httpVerb;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final Duration requestDuration;
}
