package io.graversen.requestbin.api.requestbin;

import lombok.Value;

import java.util.Map;

@Value
public class RequestBinRequestDTO {
    String binId;
    String createdAt;
    String encodedRequestBody;
    Map<String, String> queryParameters;
    Map<String, String> httpHeaders;
    String ipAddress;
    String httpVerb;
    Long requestDuration;
}
