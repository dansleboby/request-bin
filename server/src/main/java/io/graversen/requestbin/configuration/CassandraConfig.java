package io.graversen.requestbin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.List;

@Configuration
@EnableReactiveCassandraRepositories(basePackages = {"io.graversen.requestbin.data.cassandra"})
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {
    private final String contactPoints;

    public CassandraConfig(@Value("${spring.data.cassandra.contact-points}") String contactPoints) {
        this.contactPoints = contactPoints;
    }

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

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"io.graversen.requestbin.data.cassandra"};
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }
}
