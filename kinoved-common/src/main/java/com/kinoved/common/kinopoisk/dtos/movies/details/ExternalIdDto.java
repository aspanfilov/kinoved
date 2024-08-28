package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExternalIdDto {
    private String kpHD; // ID из kinopoisk HD
    private String imdb; // ID из imdb
    private Long tmdb; // ID из tmdb
}
