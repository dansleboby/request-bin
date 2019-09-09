package io.graversen.requestbin.streaming;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Flux;

import java.time.Duration;

@UtilityClass
public class StreamingUtils {
    public static Flux<RequestEvent> heartBeatStream() {
        return Flux.interval(Duration.ofSeconds(5)).map(x -> RequestEvent.heartBeat());
    }
}
