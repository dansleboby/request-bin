package io.graversen.requestbin.data.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IRequestBinRepository extends JpaRepository<RequestBinEntity, Long> {
    Collection<RequestBinEntity> findByOpenTrue();
}
