package io.graversen.requestbin.streaming;

import io.graversen.requestbin.service.requestbin.RequestBinRequest;
import lombok.NonNull;
import lombok.Value;

@Value
public class RequestEvent {
    private final @NonNull EventType eventType;
    private final RequestBinRequest data;

    private RequestEvent(EventType eventType, RequestBinRequest data) {
        this.eventType = eventType;
        this.data = data;
    }

    public static RequestEvent heartBeat() {
        return new RequestEvent(EventType.HEARTBEAT, null);
    }

    public static RequestEvent data(RequestBinRequest request) {
        return new RequestEvent(EventType.DATA, request);
    }
}
