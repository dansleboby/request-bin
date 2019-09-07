package io.graversen.requestbin.configuration;

import io.graversen.requestbin.data.mysql.IRequestBinRepository;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final IRequestBinRepository requestBinRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        requestBinRepository.deleteAll();
        requestBinRepository.save(
                new RequestBinEntity("test", LocalDateTime.now().plus(Duration.ofDays(1)), "test")
        );
    }
}
