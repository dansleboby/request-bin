package io.graversen.requestbin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = {"io.graversen.requestbin.data.cassandra"})
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
    @Override
    protected String getKeyspaceName() {
        return "request_bin";
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace(getKeyspaceName())
                        .ifNotExists()
                        .withSimpleReplication();

        return List.of(specification);
    }
}
