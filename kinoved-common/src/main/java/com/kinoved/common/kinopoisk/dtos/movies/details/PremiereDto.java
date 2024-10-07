package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class PremiereDto {
    private String country; // example: США
    private String world; // ($date-time) example: 2023-02-25T02:44:39.359Z
    // Для более релевантного поиска, используйте интервал дат 01.02.2022-01.02.2023
    private String russia; // ($date-time) example: 2023-02-25T02:44:39.359Z
    // Для более релевантного поиска, используйте интервал дат 01.02.2022-01.02.2023
    private String digital;
    private String cinema; // ($date-time) example: 2023-02-25T02:44:39.359Z
    // Для более релевантного поиска, используйте интервал дат 01.02.2022-01.02.2023
    private String bluray;
    private String dvd;
}
