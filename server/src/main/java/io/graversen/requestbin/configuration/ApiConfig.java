package io.graversen.requestbin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@Configuration
@ComponentScan(basePackages = "io.graversen.requestbin.api")
public class ApiConfig {
    @Bean
    public RouterFunction<ServerResponse> debugRouter(@Value("classpath:/public/debug.html") Resource html) {
        return route(GET("/debug"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(html));
    }
}
