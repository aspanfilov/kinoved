package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

@Data
public class ShortImageDto {
    private String url; //Чтобы найти фильмы с этим полем, используйте: !null
    private String previewUrl; //Чтобы найти фильмы с этим полем, используйте: !null
}
