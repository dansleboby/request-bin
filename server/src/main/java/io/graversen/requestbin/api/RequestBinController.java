package io.graversen.requestbin.api;

import io.graversen.requestbin.data.cassandra.IRequestByRequestBinRepository;
import io.graversen.requestbin.data.dto.RequestBinCreated;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import io.graversen.requestbin.data.service.CreateRequest;
import io.graversen.requestbin.data.service.CreateRequestBin;
import io.graversen.requestbin.service.RequestBinService;
import io.graversen.requestbin.streaming.Clients;
import io.graversen.requestbin.streaming.RequestEvent;
import io.graversen.requestbin.streaming.StreamingUtils;
import io.graversen.requestbin.util.Utils;
import lombok.RequiredArgsConstructor;
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
public class RequestBinController {
    private static final Base64.Encoder BASE_64_ENCODER = Base64.getEncoder();
    private final RequestBinService requestBinService;
    private final Clients clients;
    private final IRequestByRequestBinRepository requestByRequestBinRepository;

    @PostMapping
    public ResponseEntity<RequestBinCreated> createBin(ServerHttpRequest serverHttpRequest) {
        final String clientIp = Utils.getIpAddress(serverHttpRequest);
        final String userAgent = Utils.getUserAgent(serverHttpRequest);

        final var createRequestBin = new CreateRequestBin(clientIp, userAgent);
        final RequestBinEntity requestBinEntity = requestBinService.createNew(createRequestBin);

        final var requestBinCreated = new RequestBinCreated(requestBinEntity.getBinId());

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
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS
            }
    )
    public ResponseEntity<Void> addToBin(
            @PathVariable String binId,
            @RequestBody(required = false) String requestBody,
            @RequestParam(required = false) Map<String, String> requestParams,
            ServerHttpRequest serverHttpRequest
    ) {
        final var start = LocalTime.now();
        final Optional<RequestBinEntity> requestBinEntityOptional = requestBinService.getByBinId(binId);

        if (requestBinEntityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final var requestBinEntity = requestBinEntityOptional.get();

        if (!requestBinEntity.isOpen()) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        requestBody = Objects.requireNonNullElse(requestBody, "");

        final Map<String, String> httpHeaders = serverHttpRequest.getHeaders().toSingleValueMap();
        final String clientIp = Utils.getIpAddress(serverHttpRequest);
        final String httpVerb = serverHttpRequest.getMethodValue();
        final String encodedRequestBody = BASE_64_ENCODER.encodeToString(requestBody.getBytes());
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
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "{binId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<RequestEvent>> requestStream(@PathVariable String binId) {
        if (!requestBinService.requestBinExists(binId)) {
            return ResponseEntity.notFound().build();
        }

        final String clientId = Utils.clientId();
        final Flux<RequestEvent> eventStream = Flux.create(sink -> clients.register(clientId, binId, sink::next));
        final Flux<RequestEvent> mergedEventStream = Flux.merge(eventStream, StreamingUtils.heartBeatStream())
                .doOnCancel(() -> clients.unregister(clientId));

        return ResponseEntity.ok(mergedEventStream);
    }
}
