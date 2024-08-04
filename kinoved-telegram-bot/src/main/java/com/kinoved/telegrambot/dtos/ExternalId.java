package com.kinoved.telegrambot.dtos;

import lombok.Data;

@Data
public class ExternalId {
    private Long kpDev; // ID из kinopoisk.dev
    private String kpHD; // ID из kinopoisk HD
    private String imdb; // ID из imdb
    private Long tmdb; // ID из tmdb
}
