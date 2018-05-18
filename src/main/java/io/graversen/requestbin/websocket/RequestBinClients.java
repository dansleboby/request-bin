package io.graversen.requestbin.websocket;

import io.graversen.fiber.server.management.INetworkClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class RequestBinClients
{
    private final Map<String, List<INetworkClient>> binToNetworkClientMap;

    public RequestBinClients()
    {
        this.binToNetworkClientMap = new ConcurrentHashMap<>();
    }

    public void associate(String binIdentifier, INetworkClient networkClient)
    {
        final List<INetworkClient> networkClients = binToNetworkClientMap.getOrDefault(binIdentifier, new ArrayList<>());
        networkClients.add(networkClient);
        binToNetworkClientMap.put(binIdentifier, networkClients);
    }

    public Optional<List<INetworkClient>> getClients(String binIdentifier)
    {
        return Optional.ofNullable(binToNetworkClientMap.get(binIdentifier));
    }
}
