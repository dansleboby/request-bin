package io.graversen.requestbin.services;

import io.graversen.requestbin.websocket.RequestBinClients;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpRequestService implements IHttpRequestService
{
    private final RequestBinClients requestBinClients;

    @Autowired
    public HttpRequestService(RequestBinClients requestBinClients)
    {
        this.requestBinClients = requestBinClients;
    }
}
