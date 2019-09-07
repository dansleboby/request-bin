package io.graversen.requestbin.configuration;

import io.graversen.requestbin.data.cassandra.IRequestByRequestBinRepository;
import io.graversen.requestbin.data.cassandra.RequestByRequestBinEntity;
import io.graversen.requestbin.data.mysql.IRequestBinRepository;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "io.graversen.requestbin.service")
public class AppConfig {
    private final IRequestBinRepository requestBinRepository;
    private final IRequestByRequestBinRepository requestByRequestBinRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        requestBinRepository.deleteAll();
        requestBinRepository.save(
                new RequestBinEntity("test", LocalDateTime.now().plus(Duration.ofDays(1)), "test")
        );

        requestByRequestBinRepository.deleteAll().block();
        requestByRequestBinRepository.save(
                new RequestByRequestBinEntity("test", "body", "query params", "http headers", "ip address", "GET", "dunno lol")
        ).block();
        requestByRequestBinRepository.findAll().collectList().block();
    }
}
