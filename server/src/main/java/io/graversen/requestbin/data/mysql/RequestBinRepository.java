package io.graversen.requestbin.data.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RequestBinRepository extends JpaRepository<RequestBinEntity, Long> {
    Collection<RequestBinEntity> findByOpenTrue();

    Optional<RequestBinEntity> findByBinId(String binId);

    boolean existsByBinIdAndOpenTrue(String binId);
}
