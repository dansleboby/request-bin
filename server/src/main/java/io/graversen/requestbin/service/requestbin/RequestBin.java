package io.graversen.requestbin.service.requestbin;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RequestBin {
    private final @NonNull Long id;
    private final @NonNull String binId;
    private final @NonNull LocalDateTime createdAt;
    private final @NonNull LocalDateTime expiresAt;
    private final @NonNull String createdBy;
    private final @NonNull boolean open;
}
