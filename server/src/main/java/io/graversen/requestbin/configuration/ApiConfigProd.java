package io.graversen.requestbin.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@Profile("prod")
@ComponentScan(basePackages = "io.graversen.requestbin.api")
public class ApiConfigProd implements WebFluxConfigurer {

}
