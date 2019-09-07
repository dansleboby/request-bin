package io.graversen.requestbin.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {

    }
}
