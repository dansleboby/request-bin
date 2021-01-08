package io.graversen.requestbin.service.requestbin;

import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class RequestBin {
    @NonNull Long id;
    @NonNull String binId;
    @NonNull LocalDateTime createdAt;
    LocalDateTime expiresAt;
    @NonNull String createdBy;
    @NonNull String source;
    @NonNull boolean open;
}
