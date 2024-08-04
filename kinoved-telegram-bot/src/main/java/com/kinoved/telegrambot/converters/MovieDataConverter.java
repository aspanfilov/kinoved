package com.kinoved.telegrambot.converters;

import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieDtoV14;
import com.kinoved.telegrambot.dtos.MovieDto;

public interface MovieDataConverter {
    String convertToShortOverview(MovieDto movieDto);

    String convertToFullOverview(MovieDto movieDto);

    String convertToIdentificationOverview(SearchMovieDtoV14 movieDto);
}
