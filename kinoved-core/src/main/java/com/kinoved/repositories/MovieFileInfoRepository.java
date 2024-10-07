package com.kinoved.repositories;

import com.kinoved.models.MovieFileInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieFileInfoRepository extends MongoRepository<MovieFileInfo, String> {
}
