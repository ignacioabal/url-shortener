package com.ignacioabal.url_shortener.repositories;

import com.ignacioabal.url_shortener.models.UrlAlias;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends MongoRepository<UrlAlias, Long> {

    @Query("{'alias': ?0}")
    Optional<UrlAlias> findUrlByAlias(String alias);

    @Query(value = "{'alias': ?0}", delete = true)
    void deleteUrlByAlias(String alias) throws ResponseStatusException;

    @Query(value = "'alias': ?0")
    boolean existsByAlias(String alias);
}
