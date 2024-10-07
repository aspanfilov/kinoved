package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class PersonInMovieDto {
    private Long id; // Id персоны с кинопоиска
    private String photo; // Ссылка на фото
    private String name; // Имя персоны
    private String enName; // Имя персоны на английском
    private String description;
    private String profession;
    private String enProfession;

}
