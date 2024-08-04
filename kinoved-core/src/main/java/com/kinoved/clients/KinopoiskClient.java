package com.kinoved.clients;

import com.kinoved.common.kinopoisk.dtos.movies.MovieDtoV14;
import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieResponseDtoV14;

public interface KinopoiskClient {

    SearchMovieResponseDtoV14 searchMovie(String query, Integer page, Integer limit);

    MovieDtoV14 getMovieById(Long id);
}
