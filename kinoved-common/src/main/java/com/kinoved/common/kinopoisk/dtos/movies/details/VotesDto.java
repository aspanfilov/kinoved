package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class VotesDto {
    private int kp; // кол-во голосов кинопоиска
    private int imdb; // кол-во голосов IMDB
    private int tmdb; // кол-во голосов TMDB
    private int filmCritics; // кол-во голосов кинокритиков
    private int russianFilmCritics; // кол-во голосов кинокритиков из РФ
    private int await; // кол-во голосов ожиданий пользователей
}
