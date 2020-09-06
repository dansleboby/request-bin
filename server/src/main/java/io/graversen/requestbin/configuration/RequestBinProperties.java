package io.graversen.requestbin.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "request-bin")
public class RequestBinProperties {
    private int maxFetchSize;
    private String adminSecret;
    private Duration binExpiryDuration;
    private List<String> persistentBins;
}
