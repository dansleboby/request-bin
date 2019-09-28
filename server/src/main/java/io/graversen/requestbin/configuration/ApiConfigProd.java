package io.graversen.requestbin.configuration;

import io.graversen.requestbin.data.mysql.IRequestBinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
@Profile("prod")
@EnableScheduling
@ComponentScan(basePackages = "io.graversen.requestbin.api")
@RequiredArgsConstructor
public class ApiConfigProd implements WebFluxConfigurer {
    private static final Duration BIN_EXPIRY = Duration.ofHours(24);
    private final IRequestBinRepository requestBinRepository;

    @Scheduled(fixedDelayString = "PT1H")
    public void cleanUpRequestBins() {
        final var now = LocalDateTime.now();
        final var deleteAt = now.minus(BIN_EXPIRY);

        requestBinRepository.findByOpenTrue().stream()
                .filter(requestBinEntity -> requestBinEntity.getCreatedAt().isBefore(deleteAt))
                .forEach(requestBinEntity -> requestBinEntity.setOpen(false));
    }
}
