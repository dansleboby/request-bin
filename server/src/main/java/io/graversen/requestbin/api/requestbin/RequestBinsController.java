package io.graversen.requestbin.api.requestbin;

import io.graversen.requestbin.configuration.RequestBinProperties;
import io.graversen.requestbin.configuration.Sources;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import io.graversen.requestbin.service.requestbin.CreateRequest;
import io.graversen.requestbin.service.requestbin.CreateRequestBin;
import io.graversen.requestbin.service.requestbin.RequestBinService;
import io.graversen.requestbin.streaming.Clients;
import io.graversen.requestbin.streaming.RequestEvent;
import io.graversen.requestbin.streaming.StreamingUtils;
import io.graversen.requestbin.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RequestBinsController {
    private static final Base64.Encoder BASE_64_ENCODER = Base64.getEncoder();

    private final Clients clients;
    private final RequestBinService requestBinService;
    private final RequestBinProperties requestBinProperties;

    @PostMapping
    public ResponseEntity<RequestBinCreatedDTO> createBin(ServerHttpRequest serverHttpRequest) {
        final String clientIp = Utils.getIpAddress(serverHttpRequest);
        final String userAgent = Utils.getUserAgent(serverHttpRequest);

        final var createRequestBin = new CreateRequestBin(clientIp, userAgent, Sources.CLIENT, null, false);
        final RequestBinEntity requestBinEntity = requestBinService.createNew(createRequestBin);

        final var requestBinCreated = new RequestBinCreatedDTO(requestBinEntity.getBinId());

        return ResponseEntity.ok(requestBinCreated);
    }

    @RequestMapping(
            value = "{binId}",
            method = {
                    RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.PATCH,
                    RequestMethod.DELETE,
            }
    )
    public ResponseEntity<Void> addToBin(
            @PathVariable String binId,
            @RequestParam(required = false) Map<String, String> requestParams,
            ServerHttpRequest serverHttpRequest
    ) {
        final Optional<RequestBinEntity> requestBinEntityOptional = requestBinService.getByBinId(binId);

        if (requestBinEntityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final var requestBinEntity = requestBinEntityOptional.get();

        if (!requestBinEntity.isOpen()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        serverHttpRequest.getBody().subscribe(dataBuffer -> {
            final var start = LocalTime.now();

            final byte[] requestBodyBytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(requestBodyBytes);
            DataBufferUtils.release(dataBuffer);

            final Map<String, String> httpHeaders = serverHttpRequest.getHeaders().toSingleValueMap();
            final String clientIp = Utils.getIpAddress(serverHttpRequest);
            final String httpVerb = serverHttpRequest.getMethodValue();
            final String encodedRequestBody = BASE_64_ENCODER.encodeToString(requestBodyBytes);
            final Duration duration = Duration.between(start, LocalTime.now());

            final var createRequest = new CreateRequest(
                    binId,
                    encodedRequestBody,
                    requestParams,
                    httpHeaders,
                    clientIp,
                    httpVerb,
                    duration
            );

            requestBinService.createNewRequest(createRequest);
        });

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{binId}/status")
    public ResponseEntity<Void> requestBinExists(@PathVariable String binId) {
        if (!requestBinService.requestBinExists(binId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "{binId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<RequestEvent>> requestStream(@PathVariable String binId) {
        if (!requestBinService.requestBinExists(binId)) {
            return ResponseEntity.notFound().build();
        }

        final String clientId = Utils.clientId();
        final Flux<RequestEvent> eventStream = Flux.create(sink -> clients.register(clientId, binId, sink::next));
        final Flux<RequestEvent> mergedEventStream = Flux.merge(eventStream, StreamingUtils.heartBeatStream())
                .doOnCancel(() -> clients.unregister(clientId))
                .doOnError(e -> clients.unregister(clientId))
                .doOnTerminate(() -> clients.unregister(clientId));

        return ResponseEntity.ok(mergedEventStream);
    }

    @GetMapping(value = "{binId}/stream/{fetchCount}")
    public ResponseEntity<Void> getLatest(@PathVariable String binId, @PathVariable Integer fetchCount) {
        if (!requestBinService.requestBinExists(binId)) {
            return ResponseEntity.notFound().build();
        }

        fetchCount = Math.max(fetchCount, 0);
        fetchCount = Math.min(fetchCount, requestBinProperties.getMaxFetchSize());

        requestBinService.emitLatest(binId, fetchCount);
        return ResponseEntity.ok().build();
    }
}
