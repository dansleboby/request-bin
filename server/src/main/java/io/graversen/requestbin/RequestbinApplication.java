package io.graversen.requestbin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;

@SpringBootApplication(
        scanBasePackages = "io.graversen.requestbin.configuration"
)
public class RequestbinApplication {
    public static void main(String[] args) {
        System.out.println(Duration.ofDays(7));
        SpringApplication.run(RequestbinApplication.class, args);
    }
}
