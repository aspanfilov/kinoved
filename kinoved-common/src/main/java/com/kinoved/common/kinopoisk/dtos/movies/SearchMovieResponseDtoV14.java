package com.kinoved.common.kinopoisk.dtos.movies;

import lombok.Data;

import java.util.List;

@Data
public class SearchMovieResponseDtoV14 {
    private List<SearchMovieDtoV14> docs;
    private int total; // общее кол-во результатов
    private int limit; // кол-во результатов на странице
    private int page; // текущая страница
    private int pages; // сколько страниц всего
}
