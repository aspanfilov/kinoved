package com.kinoved.common.telegram.dtos;

import com.kinoved.common.enums.kinopoisk.KinopoiskImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieImageDto {
    private Long movieId;
    private KinopoiskImageType type;
    private String url;
    private String previewUrl;
    private Integer height;
    private Integer width;
    private String externalId;
}
