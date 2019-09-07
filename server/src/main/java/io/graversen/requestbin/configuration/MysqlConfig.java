package io.graversen.requestbin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"io.graversen.requestbin.data.mysql"})
public class MysqlConfig {

}
