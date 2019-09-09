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

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestBinService {
    private final ISerializer SERIALIZER = new GsonSerializer();
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
        final var httpHeaders = SERIALIZER.serialize(createRequest.getHttpHeaders());
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

    public boolean requestBinExists(String binId) {
        return requestBinRepository.existsByBinIdAndOpenTrue(binId);
    }

    public Optional<RequestBinEntity> getByBinId(String binId) {
        return requestBinRepository.findByBinId(binId);
    }

    private Function<RequestByRequestBinEntity, Request> requestDtoMapper() {
        return entity -> new Request(
                entity.getBinId(),
                entity.getCreatedAt(),
                entity.getRequestBody(),
                entity.getQueryParameters(),
                entity.getHttpHeaders(),
                entity.getIpAddress(),
                entity.getHttpVerb(),
                entity.getRequestDuration()
        );
    }

    private Consumer<RequestEvent> emitRequestEvent(String binId) {
        return requestEvent -> clients.emit(binId, requestEvent);
    }
}
