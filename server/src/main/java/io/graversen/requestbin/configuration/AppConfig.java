package io.graversen.requestbin.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = {"io.graversen.requestbin.service", "io.graversen.requestbin.streaming"})
public class AppConfig {

}
