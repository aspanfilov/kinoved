package com.kinoved.services.impl;

import com.kinoved.enums.MovieFileStatus;
import com.kinoved.models.MovieData;
import com.kinoved.models.MovieFileInfo;
import com.kinoved.repositories.MovieFileInfoRepository;
import com.kinoved.services.MovieFileInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieFileInfoServiceImpl implements MovieFileInfoService {

    private final MovieFileInfoRepository movieFileInfoRepository;

    @Override
    public Optional<MovieFileInfo> findById(String id) {
        return movieFileInfoRepository.findById(id);
    }

    @Override
    public MovieFileInfo insert(MovieFileInfo movieFileInfo) {
        movieFileInfo.setId(null);
        return save(movieFileInfo);
    }

    @Override
    public MovieFileInfo update(MovieFileInfo movieFileInfo) {
        return save(movieFileInfo);
    }

    @Override
    public MovieFileInfo updateMatchMovieData(MovieFileInfo movieFileInfo, MovieData movieData) {
        movieFileInfo.setStatus(MovieFileStatus.MATCHED);
        movieFileInfo.setMovieData(movieData);
        return update(movieFileInfo);
    }

    private MovieFileInfo save(MovieFileInfo movieFileInfo) {
        return movieFileInfoRepository.save(movieFileInfo);
    }
}
