package io.graversen.requestbin.data.cassandra;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface IRequestByRequestBinRepository extends ReactiveCassandraRepository<RequestByRequestBinEntity, Void> {

}
