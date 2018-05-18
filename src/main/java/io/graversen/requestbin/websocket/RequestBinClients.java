package io.graversen.requestbin.websocket;

import io.graversen.fiber.server.management.INetworkClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestBinClients
{
    private final Map<String, INetworkClient> binToNetworkClientMap;

    public RequestBinClients()
    {
        this.binToNetworkClientMap = new ConcurrentHashMap<>();
    }
}
