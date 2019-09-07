package io.graversen.requestbin.services;

import io.graversen.requestbin.models.service.CreateHttpRequest;
import io.graversen.requestbin.models.service.HttpRequest;

public interface IHttpRequestService
{
    void create(CreateHttpRequest createHttpRequest);

    void emitHttpRequest(String binIdentifier, Iterable<HttpRequest> httpRequests);
}
