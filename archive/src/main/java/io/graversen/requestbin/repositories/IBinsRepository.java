package io.graversen.requestbin.repositories;

import io.graversen.requestbin.models.entities.BinsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBinsRepository extends CrudRepository<BinsEntity, Long>
{
    Optional<BinsEntity> findByIdentifier(String identifier);
}
