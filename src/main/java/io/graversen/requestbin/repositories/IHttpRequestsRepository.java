package io.graversen.requestbin.repositories;

import io.graversen.requestbin.models.entities.HttpRequestsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHttpRequestsRepository extends PagingAndSortingRepository<HttpRequestsEntity, Long>
{

}
