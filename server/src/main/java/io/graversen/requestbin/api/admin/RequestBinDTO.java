package io.graversen.requestbin.api.admin;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RequestBinDTO {
    private final String binId;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;
    private final String createdBy;
}
