package io.graversen.requestbin.service.requestbin;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateRequestBin {
    private final @NonNull String ipAddress;
    private final @NonNull String userAgent;
    private final String binId;
}
