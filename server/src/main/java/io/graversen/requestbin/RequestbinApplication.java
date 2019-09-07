package io.graversen.requestbin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = "io.graversen.requestbin.configuration"
)
public class RequestbinApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestbinApplication.class, args);
    }
}
