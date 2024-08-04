package com.kinoved.repositories;

import com.kinoved.models.MovieData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieDataRepository extends MongoRepository<MovieData, String> {
    Optional<MovieData> findByExternalId_KpDev(Long kpDev);
}
