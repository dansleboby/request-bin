package io.graversen.requestbin.streaming;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
public class Clients {
    private final List<Client> requestEventConsumers = new CopyOnWriteArrayList<>();

    public void register(String clientId, String binId, Consumer<RequestEvent> sink) {
        requestEventConsumers.add(new Client(clientId, binId, sink));
    }

    public void unregister(String clientId) {
        requestEventConsumers.stream()
                .filter(client -> client.getClientId().equals(clientId))
                .findFirst()
                .ifPresent(requestEventConsumers::remove);
    }

    public void emit(String binId, RequestEvent requestEvent) {
        System.out.println("Clients.emit");
        requestEventConsumers.stream()
                .filter(client -> client.getBinId().equals(binId))
                .forEach(client -> client.getSink().accept(requestEvent));
    }
}
