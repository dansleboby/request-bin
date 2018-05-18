package io.graversen.requestbin.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "io.graversen.requestbin.repositories")
@EntityScan(basePackages = "io.graversen.requestbin.models.entities")
@ComponentScan("io.graversen.requestbin")
public class RequestBin
{
    public static void main(String[] args)
    {
        SpringApplication.run(RequestBin.class, args);
    }
}
