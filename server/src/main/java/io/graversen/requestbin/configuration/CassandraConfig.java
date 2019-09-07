package io.graversen.requestbin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = {"io.graversen.requestbin.data.cassandra"})
public class CassandraConfig {

}
