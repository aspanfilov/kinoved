package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class RatingDto {
    private double kp; // рейтинг кинопоиска
    private double imdb; // рейтинг IMDB
    private double tmdb; // рейтинг TMDB
    private double filmCritics; // рейтинг кинокритиков
    private double russianFilmCritics; // рейтинг кинокритиков из РФ
    private double await; // рейтинг основанный на ожиданиях пользователей
}
