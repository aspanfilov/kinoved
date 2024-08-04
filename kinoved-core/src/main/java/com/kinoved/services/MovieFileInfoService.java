package com.kinoved.services;

import com.kinoved.models.MovieData;
import com.kinoved.models.MovieFileInfo;

import java.util.Optional;

public interface MovieFileInfoService {

    Optional<MovieFileInfo> findById(String id);

    MovieFileInfo insert(MovieFileInfo movieFileInfo);

    MovieFileInfo update(MovieFileInfo movieFileInfo);

    MovieFileInfo updateMatchMovieData(MovieFileInfo movieFileInfo, MovieData movieData);
}
