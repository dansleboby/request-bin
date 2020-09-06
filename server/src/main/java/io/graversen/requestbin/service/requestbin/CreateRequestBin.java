package io.graversen.requestbin.service.requestbin;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateRequestBin {
    @NonNull
    private final String ipAddress;

    @NonNull
    private final String userAgent;
}
