package io.graversen.requestbin.data.service;

import lombok.Value;

@Value
public class CreateRequestBin {
    private final String ipAddress;
    private final String userAgent;
}
