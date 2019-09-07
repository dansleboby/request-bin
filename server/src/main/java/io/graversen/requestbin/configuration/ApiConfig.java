package io.graversen.requestbin.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "io.graversen.requestbin.api")
public class ApiConfig {
}
