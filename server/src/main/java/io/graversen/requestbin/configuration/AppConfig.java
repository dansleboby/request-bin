package io.graversen.requestbin.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RequestBinProperties.class)
@ComponentScan(basePackages = {"io.graversen.requestbin.service", "io.graversen.requestbin.streaming"})
public class AppConfig {

}
