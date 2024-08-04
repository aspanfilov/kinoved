package com.kinoved.services.impl;

import com.kinoved.models.MovieData;
import com.kinoved.repositories.MovieDataRepository;
import com.kinoved.services.MovieDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieDataServiceImpl implements MovieDataService {

    private final MovieDataRepository movieDataRepository;

    @Override
    public List<MovieData> getAllMovies() {
        return movieDataRepository.findAll();
    }

    @Override
    public Optional<MovieData> getMovieById(String id) {
        return movieDataRepository.findById(id);
    }

    @Override
    public Optional<MovieData> getMovieByKpDevId(Long kpDevId) {
        return movieDataRepository.findByExternalId_KpDev(kpDevId);
    }

}
