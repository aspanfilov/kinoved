package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class ReviewInfoDto {
    private Integer count;
    private Integer positiveCount;
    private String percentage;
}
