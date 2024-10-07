package com.kinoved.services.impl;

import com.kinoved.clients.KinopoiskClient;
import com.kinoved.common.kinopoisk.dtos.movies.MovieDtoV14;
import com.kinoved.mappers.MovieDataMapper;
import com.kinoved.models.MovieData;
import com.kinoved.repositories.MovieDataRepository;
import com.kinoved.services.MovieDataLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieDataLoaderServiceImpl implements MovieDataLoaderService {

    private final KinopoiskClient kinopoiskClient;

    private final MovieDataMapper movieDataMapper;

    private final MovieDataRepository movieDataRepository;

    @Override
    public MovieData loadAndSaveMovieDataByKpDevId(Long kpDevId) {
        MovieDtoV14 movieDto = kinopoiskClient.getMovieById(kpDevId);
        MovieData movieData = movieDataMapper.toEntity(movieDto);
        return movieDataRepository.save(movieData);
    }
}
