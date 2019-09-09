package io.graversen.requestbin.streaming;

import io.graversen.requestbin.data.dto.Request;
import lombok.Value;

@Value
public class RequestEvent {
    private final EventType eventType;
    private final Request data;

    private RequestEvent(EventType eventType, Request data) {
        this.eventType = eventType;
        this.data = data;
    }

    public static RequestEvent heartBeat() {
        return new RequestEvent(EventType.HEARTBEAT, null);
    }

    public static RequestEvent data(Request request) {
        return new RequestEvent(EventType.DATA, request);
    }
}
