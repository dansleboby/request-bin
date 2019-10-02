package io.graversen.requestbin.service;

import io.graversen.requestbin.data.cassandra.IRequestByRequestBinRepository;
import io.graversen.requestbin.data.cassandra.RequestByRequestBinEntity;
import io.graversen.requestbin.data.dto.Request;
import io.graversen.requestbin.data.mysql.IRequestBinRepository;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import io.graversen.requestbin.data.service.CreateRequest;
import io.graversen.requestbin.data.service.CreateRequestBin;
import io.graversen.requestbin.streaming.Clients;
import io.graversen.requestbin.streaming.RequestEvent;
import io.graversen.requestbin.util.Utils;
import io.graversen.trunk.io.serialization.interfaces.ISerializer;
import io.graversen.trunk.io.serialization.json.GsonSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestBinService {
    private static final List<String> HTTP_HEADER_BLACKLIST = List.of(
            "X-Forwarded-For",
            "X-Forwarded-Port",
            "X-Forwarded-Proto",
            "Host"
    );
    private static final ISerializer SERIALIZER = new GsonSerializer();

    private final IRequestBinRepository requestBinRepository;
    private final IRequestByRequestBinRepository requestByRequestBinRepository;
    private final Clients clients;

    public RequestBinEntity createNew(CreateRequestBin createRequestBin) {
        final var binId = Utils.binId();
        final var expiresAt = Utils.defaultBinExpiry();
        final var createdBy = Utils.generateCreatedByString(
                createRequestBin.getIpAddress(),
                createRequestBin.getUserAgent()
        );

        return requestBinRepository.save(
                new RequestBinEntity(binId, expiresAt, createdBy)
        );
    }

    public void createNewRequest(CreateRequest createRequest) {
        final var httpHeaders = SERIALIZER.serialize(sanitizeHttpHeaders(createRequest.getHttpHeaders()));
        final var queryParameters = SERIALIZER.serialize(createRequest.getQueryParameters());
        final var duration = createRequest.getRequestDuration().toString();

        final var requestEntity = new RequestByRequestBinEntity(
                createRequest.getBinId(),
                createRequest.getRequestBody(),
                queryParameters,
                httpHeaders,
                createRequest.getIpAddress(),
                createRequest.getHttpVerb(),
                duration
        );

        requestByRequestBinRepository.save(requestEntity)
                .map(requestDtoMapper())
                .map(RequestEvent::data)
                .subscribe(emitRequestEvent(createRequest.getBinId()));
    }

    public void emitLatest(String binId, int amount) {
        requestByRequestBinRepository.findAllByBinId(binId)
                .limitRequest(amount)
                .map(requestDtoMapper())
                .map(RequestEvent::data)
                .subscribe(emitRequestEvent(binId));
    }

    public boolean requestBinExists(String binId) {
        return requestBinRepository.existsByBinIdAndOpenTrue(binId);
    }

    public Optional<RequestBinEntity> getByBinId(String binId) {
        return requestBinRepository.findByBinId(binId);
    }

    private Function<RequestByRequestBinEntity, Request> requestDtoMapper() {
        final var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return entity -> {
            final Map<String, String> queryParameters = safeDeserialize(entity.getQueryParameters());
            final Map<String, String> httpHeaders = safeDeserialize(entity.getHttpHeaders());
            final String createdAt = safeParseDate(entity.getCreatedAt(), dateTimeFormatter);
            final long durationMillis = safeParseDuration(entity.getRequestDuration());

            return new Request(
                    entity.getBinId(),
                    createdAt,
                    entity.getRequestBody(),
                    queryParameters,
                    httpHeaders,
                    entity.getIpAddress(),
                    entity.getHttpVerb(),
                    durationMillis
            );
        };
    }

    private Consumer<RequestEvent> emitRequestEvent(String binId) {
        return requestEvent -> clients.emit(binId, requestEvent);
    }

    private Map<String, String> safeDeserialize(String data) {
        try {
            return SERIALIZER.deserialize(data, Map.class);
        } catch (Exception e) {
            return Map.of();
        }
    }

    private String safeParseDate(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        try {
            return localDateTime.format(dateTimeFormatter);
        } catch (Exception e) {
            return "";
        }
    }

    private long safeParseDuration(String duration) {
        try {
            return Duration.parse(duration).toMillis();
        } catch (Exception e) {
            return 0;
        }
    }

    private Map<String, String> sanitizeHttpHeaders(Map<String, String> httpHeaders) {
        final var modifiableMap = new HashMap<>(httpHeaders);
        modifiableMap.keySet().removeAll(HTTP_HEADER_BLACKLIST);
        return Map.copyOf(modifiableMap);
    }
}
