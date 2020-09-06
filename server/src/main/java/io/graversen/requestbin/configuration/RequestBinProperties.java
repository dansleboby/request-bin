package io.graversen.requestbin.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;
import java.util.List;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "request-bin")
public class RequestBinProperties {
    private final int maxFetchSize;
    private final String adminSecret;
    private final Duration binExpiryDuration;
    private final List<String> persistentBins;
}
