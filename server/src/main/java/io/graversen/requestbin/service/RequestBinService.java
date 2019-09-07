package io.graversen.requestbin.service;

import io.graversen.requestbin.data.cassandra.IRequestByRequestBinRepository;
import io.graversen.requestbin.data.mysql.IRequestBinRepository;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import io.graversen.requestbin.data.service.CreateRequestBin;
import io.graversen.requestbin.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestBinService {
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
}
