package io.graversen.requestbin.configuration;

import io.graversen.requestbin.data.mysql.IRequestBinRepository;
import io.graversen.requestbin.data.mysql.RequestBinEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Configuration
@Profile("prod")
@EnableScheduling
@ComponentScan(basePackages = "io.graversen.requestbin.api")
@RequiredArgsConstructor
@Slf4j
public class ApiConfigProd implements WebFluxConfigurer {
    private static final Duration BIN_EXPIRY = Duration.ofDays(7);
    private static final List<String> SPECIAL_BINS = List.of(
            "WAVELY-86244b38-adb9-4107-93f6-e860a4ad2c1f"
    );

    private final IRequestBinRepository requestBinRepository;

    @Scheduled(fixedDelayString = "PT1H")
    public void cleanUpRequestBins() {
        final var now = LocalDateTime.now();
        final var deleteAt = now.minus(BIN_EXPIRY);

        requestBinRepository.findByOpenTrue().stream()
                .filter(requestBinEntity -> !SPECIAL_BINS.contains(requestBinEntity.getBinId()))
                .filter(requestBinEntity -> requestBinEntity.getCreatedAt().isBefore(deleteAt))
                .peek(requestBinEntity -> log.info("Cleaning up bin: {}", requestBinEntity.getBinId()))
                .forEach(requestBinEntity -> requestBinEntity.setOpen(false));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        SPECIAL_BINS.forEach(createBinIfNotExists());
    }

    private Consumer<String> createBinIfNotExists() {
        return binId -> {
            if (!requestBinRepository.existsByBinIdAndOpenTrue(binId)) {
                log.info("Creating special bin: {}", binId);
                requestBinRepository.save(new RequestBinEntity(binId, LocalDateTime.now(), "SYSTEM"));
            }
        };
    }
}
