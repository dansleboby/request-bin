package io.graversen.requestbin.service.requestbin;

import lombok.NonNull;
import lombok.Value;

@Value
public class CreateRequestBin {
    @NonNull String ipAddress;
    @NonNull String userAgent;
    @NonNull String source;
    String binId;
    boolean persistent;
}
