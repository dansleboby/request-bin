package io.graversen.requestbin.service;

import io.graversen.requestbin.data.cassandra.IRequestByRequestBinRepository;
import io.graversen.requestbin.data.cassandra.RequestByRequestBinEntity;
import io.graversen.requestbin.data.mysql.IRequestBinRepository;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import io.graversen.requestbin.data.service.CreateRequest;
import io.graversen.requestbin.data.service.CreateRequestBin;
import io.graversen.requestbin.util.Utils;
import io.graversen.trunk.io.serialization.interfaces.ISerializer;
import io.graversen.trunk.io.serialization.json.GsonSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestBinService {
    private final ISerializer SERIALIZER = new GsonSerializer();
    private final IRequestBinRepository requestBinRepository;
    private final IRequestByRequestBinRepository requestByRequestBinRepository;

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

        requestByRequestBinRepository.save(
                new RequestByRequestBinEntity(
                        createRequest.getBinId(),
                        createRequest.getRequestBody(),
                        queryParameters,
                        httpHeaders,
                        createRequest.getIpAddress(),
                        createRequest.getHttpVerb(),
                        duration
                )
        );
    }

    public Optional<RequestBinEntity> getByBinId(String binId) {
        return requestBinRepository.findByBinId(binId);
    }
}
