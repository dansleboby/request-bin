package io.graversen.requestbin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = {"io.graversen.requestbin.data.cassandra"})
public class CassandraConfig {

}
