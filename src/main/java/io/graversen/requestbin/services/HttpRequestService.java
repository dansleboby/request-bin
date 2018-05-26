package io.graversen.requestbin.services;

import io.graversen.requestbin.models.entities.HttpRequestsEntity;
import io.graversen.requestbin.models.service.CreateHttpRequest;
import io.graversen.requestbin.models.service.HttpRequest;
import io.graversen.requestbin.repositories.IHttpRequestsRepository;
import io.graversen.requestbin.websocket.RequestBinClients;
import io.graversen.trunk.io.serialization.interfaces.ISerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Transactional
public class HttpRequestService implements IHttpRequestService
{
    private final RequestBinClients requestBinClients;
    private final IHttpRequestsRepository httpRequestsRepository;
    private final ISerializer serializer;
    private final Base64.Encoder base64Encoder;

    @Autowired
    public HttpRequestService(RequestBinClients requestBinClients, IHttpRequestsRepository httpRequestsRepository, ISerializer serializer)
    {
        this.requestBinClients = requestBinClients;
        this.httpRequestsRepository = httpRequestsRepository;
        this.serializer = serializer;
        this.base64Encoder = Base64.getEncoder();
    }

    @Override
    public void create(CreateHttpRequest createHttpRequest)
    {
        HttpRequestsEntity httpRequestsEntity = new HttpRequestsEntity();
        httpRequestsEntity.setBinId(createHttpRequest.getBinId());
        httpRequestsEntity.setCreatedAt(LocalDateTime.now());
        httpRequestsEntity.setHttpHeaders(base64Encoder.encodeToString(serializer.serialize(createHttpRequest.getHttpHeaders()).getBytes()));
        httpRequestsEntity.setQueryParameters(base64Encoder.encodeToString(serializer.serialize(createHttpRequest.getQueryParameters()).getBytes()));
        httpRequestsEntity.setIpAddress(createHttpRequest.getIpAddress());
        httpRequestsEntity.setRequestBody(base64Encoder.encodeToString(createHttpRequest.getRequestBody().getBytes()));
        httpRequestsEntity.setHttpVerb(createHttpRequest.getHttpVerb());

        httpRequestsRepository.save(httpRequestsEntity);
    }

    @Override
    public void emitHttpRequest(String binIdentifier, Iterable<HttpRequest> httpRequests)
    {
        httpRequests.forEach(httpRequest -> requestBinClients.emitHttpRequestToClients(binIdentifier, httpRequest));
    }
}
