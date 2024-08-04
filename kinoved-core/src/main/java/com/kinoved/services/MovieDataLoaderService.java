package com.kinoved.services;

import com.kinoved.models.MovieData;

public interface MovieDataLoaderService {

    MovieData loadAndSaveMovieDataByKpDevId(Long kpDevId);
}
