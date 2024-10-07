package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class FeesDto {
    private CurrencyValueDto world;
    private CurrencyValueDto russia;
    private CurrencyValueDto usa;
}
