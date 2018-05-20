package io.graversen.requestbin.websocket;

import io.graversen.fiber.server.management.INetworkClient;
import io.graversen.fiber.server.websocket.base.AbstractWebSocketServer;
import io.graversen.requestbin.models.dto.HttpRequestDTO;
import io.graversen.trunk.io.serialization.interfaces.ISerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestBinClients
{
    private final ISerializer serializer;
    private final AbstractWebSocketServer webSocketServer;
    private final Map<String, List<INetworkClient>> binToNetworkClientMap;

    @Autowired
    public RequestBinClients(ISerializer serializer, AbstractWebSocketServer webSocketServer)
    {
        this.serializer = serializer;
        this.webSocketServer = webSocketServer;
        this.binToNetworkClientMap = new ConcurrentHashMap<>();
    }

    public void associate(String binIdentifier, INetworkClient networkClient)
    {
        final List<INetworkClient> networkClients = binToNetworkClientMap.getOrDefault(binIdentifier, new ArrayList<>());
        networkClients.add(networkClient);
        binToNetworkClientMap.put(binIdentifier, networkClients);
    }

    public List<INetworkClient> getClients(String binIdentifier)
    {
        return binToNetworkClientMap.getOrDefault(binIdentifier, new ArrayList<>());
    }

    public void emitHttpRequestToClients(String binIdentifier, HttpRequestDTO httpRequest)
    {
        final String json = serializer.serialize(httpRequest);
        getClients(binIdentifier).forEach(networkClient -> webSocketServer.send(networkClient, json.getBytes()));
    }
}
