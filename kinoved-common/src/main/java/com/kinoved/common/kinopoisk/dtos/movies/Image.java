package com.kinoved.common.kinopoisk.dtos.movies;

import lombok.Data;

@Data
public class Image {
    private Long movieId;
    private String type;
    private String language;
    private String url;
    private String previewUrl;
    private Integer height;
    private Integer width;
    private String updatedAt;
    private String createdAt;
    private String id;
}
