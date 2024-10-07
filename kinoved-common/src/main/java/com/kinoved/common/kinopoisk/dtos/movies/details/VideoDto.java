package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class VideoDto {
    private String url; // Url трейлера
    private String name; // example: Official Trailer
    private String site; // example: YOUTUBE
    private Integer size;
    private String type; // example: TRAILER
}
