package io.graversen.requestbin.websocket;

import io.graversen.fiber.event.bus.AbstractEventBus;
import io.graversen.fiber.event.common.*;
import io.graversen.fiber.event.listeners.AbstractNetworkEventListener;
import io.graversen.fiber.server.management.AbstractNetworkClientManager;
import io.graversen.fiber.server.management.INetworkClient;
import io.graversen.fiber.server.websocket.base.AbstractWebSocketServer;
import io.graversen.requestbin.models.dto.HttpRequestDTO;
import io.graversen.requestbin.models.dto.SubscribeDTO;
import io.graversen.trunk.io.serialization.interfaces.ISerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestBinClients extends AbstractNetworkEventListener
{
    private final String BIN_ID_KEY = "BIN_IDENTIFIER";

    private final ISerializer serializer;
    private final AbstractWebSocketServer webSocketServer;
    private final AbstractNetworkClientManager networkClientManager;
    private final Map<String, List<INetworkClient>> binToNetworkClientMap;

    @Autowired
    public RequestBinClients(ISerializer serializer, AbstractWebSocketServer webSocketServer, AbstractEventBus eventBus, AbstractNetworkClientManager networkClientManager)
    {
        super(eventBus);
        this.serializer = serializer;
        this.webSocketServer = webSocketServer;
        this.networkClientManager = networkClientManager;
        this.binToNetworkClientMap = new ConcurrentHashMap<>();
    }

    public void associate(String binIdentifier, INetworkClient networkClient)
    {
        final List<INetworkClient> networkClients = binToNetworkClientMap.getOrDefault(binIdentifier, new ArrayList<>());
        networkClients.add(networkClient);
        binToNetworkClientMap.put(binIdentifier, networkClients);
    }

    public void dissociate(String binIdentifier, INetworkClient networkClient)
    {
        final List<INetworkClient> networkClients = binToNetworkClientMap.get(binIdentifier);
        networkClients.removeIf(client -> client.id().equals(networkClient.id()));
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

    @Override
    public void onClientConnected(ClientConnectedEvent clientConnectedEvent)
    {

    }

    @Override
    public void onClientDisconnected(ClientDisconnectedEvent clientDisconnectedEvent)
    {
        final INetworkClient networkClient = clientDisconnectedEvent.getNetworkClient();
        dissociate(networkClientManager.getData(networkClient.id(), BIN_ID_KEY), networkClient);
    }

    @Override
    public void onNetworkMessageReceived(NetworkMessageReceivedEvent networkMessageReceivedEvent)
    {
        try
        {
            final INetworkClient networkClient = networkMessageReceivedEvent.getNetworkClient();
            final SubscribeDTO subscribeDTO = serializer.deserialize(new String(networkMessageReceivedEvent.getNetworkMessage().getMessageData()), SubscribeDTO.class);
            associate(subscribeDTO.getBinIdentifier(), networkMessageReceivedEvent.getNetworkClient());
            networkClientManager.putData(networkClient.id(), BIN_ID_KEY, subscribeDTO.getBinIdentifier());

            var httpRequest = new HttpRequestDTO();
            httpRequest.setCreatedAt(LocalDateTime.now());
            httpRequest.setIpAddress("1.2.3.4");
            httpRequest.setHttpHeaders(new HashMap<>());
            httpRequest.setQueryParameters(new HashMap<>());
            httpRequest.setRequestBody(Base64.getEncoder().encodeToString(serializer.serialize(subscribeDTO).getBytes()));

            emitHttpRequestToClients(subscribeDTO.getBinIdentifier(), httpRequest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkMessageSent(NetworkMessageSentEvent networkMessageSentEvent)
    {

    }

    @Override
    public void onServerReady(ServerReadyEvent serverReadyEvent)
    {

    }

    @Override
    public void onServerClosed(ServerClosedEvent serverClosedEvent)
    {

    }
}
