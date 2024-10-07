package com.kinoved.common.kinopoisk.dtos.movies;

import com.kinoved.common.kinopoisk.dtos.movies.details.RatingDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.ShortImageDto;
import lombok.Data;

@Data
public class LinkedMovieDtoV14 {
    private Long id;
    private String name;
    private String enName;
    private String alternativeName;
    private String type;
    private ShortImageDto poster;
    private RatingDto rating;
    private Integer year;
}
