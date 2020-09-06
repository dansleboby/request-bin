package io.graversen.requestbin.configuration;

import io.graversen.requestbin.service.requestbin.CreateRequestBin;
import io.graversen.requestbin.service.requestbin.RequestBinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.function.Consumer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RequestBinProperties.class)
@ComponentScan(basePackages = {"io.graversen.requestbin.service", "io.graversen.requestbin.streaming"})
public class AppConfig {
    private final RequestBinProperties requestBinProperties;
    private final RequestBinService requestBinService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        requestBinProperties.getPersistentBins().forEach(createPersistentBinIfNotExists());
    }

    private Consumer<String> createPersistentBinIfNotExists() {
        return binId -> {
            if (!requestBinService.requestBinExists(binId)) {
                log.info("Creating persistent bin: {}", binId);
                requestBinService.createNew(new CreateRequestBin("system", "system", binId));
            }
        };
    }
}
