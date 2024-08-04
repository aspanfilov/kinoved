package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class AudienceDto {
    private int count; // Количество просмотров в кино
    private String country; // Страна в которой проходил показ
}
