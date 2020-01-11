package io.graversen.requestbin.data.service;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateRequestBin {
    @NonNull
    private final String ipAddress;

    @NonNull
    private final String userAgent;
}
