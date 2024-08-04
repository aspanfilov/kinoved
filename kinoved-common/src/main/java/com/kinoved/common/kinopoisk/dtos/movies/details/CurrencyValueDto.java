package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class CurrencyValueDto {
    private Integer value; // Сумма
    private String currency; // Валюта
}
