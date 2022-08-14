package io.graversen.requestbin.streaming;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
public class Clients {
    private final List<Client> requestEventConsumers = new CopyOnWriteArrayList<>();

    public void register(String clientId, String binId, Consumer<RequestEvent> sink) {
        log.debug("Registering new client: {}", clientId);
        requestEventConsumers.add(new Client(clientId, binId, sink));
    }

    public void unregister(String clientId) {
        log.debug("Un-registering existing client: {}", clientId);
        requestEventConsumers.stream()
                .filter(client -> client.getClientId().equals(clientId))
                .findFirst()
                .ifPresent(requestEventConsumers::remove);
    }

    public void emit(String binId, RequestEvent requestEvent) {
        final var targetClients = requestEventConsumers.stream()
                .filter(client -> client.getBinId().equals(binId))
                .collect(Collectors.toUnmodifiableList());

        if (!targetClients.isEmpty()) {
            log.debug("Emitting an event to {} clients listening to {}", targetClients.size(), binId);
            targetClients.forEach(client -> client.getSink().accept(requestEvent));
        }
    }
}
