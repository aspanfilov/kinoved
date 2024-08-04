package com.kinoved.services;

import com.kinoved.models.MovieData;

import java.util.List;
import java.util.Optional;

public interface MovieDataService {
    List<MovieData> getAllMovies();

    Optional<MovieData> getMovieById(String id);

    Optional<MovieData> getMovieByKpDevId(Long kpDevId);
}
