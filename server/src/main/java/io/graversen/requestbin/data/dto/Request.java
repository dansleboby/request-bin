package io.graversen.requestbin.data.dto;

import lombok.Value;

import java.util.Map;

@Value
public class Request {
    private final String binId;
    private final String createdAt;
    private final String encodedRequestBody;
    private final Map<String, String> queryParameters;
    private final Map<String, String> httpHeaders;
    private final String ipAddress;
    private final String httpVerb;
    private final Long requestDuration;
}
