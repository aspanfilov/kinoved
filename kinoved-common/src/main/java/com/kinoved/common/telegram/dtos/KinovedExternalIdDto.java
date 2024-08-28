package com.kinoved.common.telegram.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KinovedExternalIdDto {
    private Long kpDev; // ID из kinopoisk.dev
    private String kpHD; // ID из kinopoisk HD
    private String imdb; // ID из imdb
    private Long tmdb; // ID из tmdb
}
