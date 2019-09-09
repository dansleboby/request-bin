package io.graversen.requestbin.streaming;

import lombok.Value;

import java.util.function.Consumer;

@Value
class Client {
    private final String clientId;
    private final String binId;
    private final Consumer<RequestEvent> sink;
}
