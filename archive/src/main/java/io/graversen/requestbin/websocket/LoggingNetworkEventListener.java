package io.graversen.requestbin.websocket;

import io.graversen.fiber.event.bus.AbstractEventBus;
import io.graversen.fiber.event.common.*;
import io.graversen.fiber.event.listeners.AbstractNetworkEventListener;

public class LoggingNetworkEventListener extends AbstractNetworkEventListener
{
    public LoggingNetworkEventListener(AbstractEventBus abstractEventBus)
    {
        super(abstractEventBus);
    }

    @Override
    public void onClientConnected(ClientConnectedEvent event)
    {
        event.print();
    }

    @Override
    public void onClientDisconnected(ClientDisconnectedEvent event)
    {
        event.print();
    }

    @Override
    public void onNetworkMessageReceived(NetworkMessageReceivedEvent event)
    {
        event.print();
    }

    @Override
    public void onNetworkMessageSent(NetworkMessageSentEvent event)
    {
        event.print();
    }

    @Override
    public void onServerReady(ServerReadyEvent event)
    {
        event.print();
    }

    @Override
    public void onServerClosed(ServerClosedEvent event)
    {
        event.print();
    }
}
