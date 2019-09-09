package io.graversen.requestbin.data.cassandra;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface IRequestByRequestBinRepository extends ReactiveCassandraRepository<RequestByRequestBinEntity, Void> {
    Flux<RequestByRequestBinEntity> findByBinId(String binId);
    Flux<RequestByRequestBinEntity> findByBinIdAndBucketAfter(String binId, UUID bucket);
}
