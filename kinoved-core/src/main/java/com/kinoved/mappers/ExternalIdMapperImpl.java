package com.kinoved.mappers;

import com.kinoved.common.kinopoisk.dtos.movies.details.ExternalIdDto;
import com.kinoved.models.moviedetails.ExternalId;
import org.springframework.stereotype.Component;

@Component
public class ExternalIdMapperImpl implements ExternalIdMapper {
    @Override
    public ExternalId toEntity(Long kpDevId, ExternalIdDto dto) {
        if (kpDevId == null) {
            throw new IllegalArgumentException("kpDevId cannot be null");
        }
        return ExternalId.builder()
                .kpDev(kpDevId)
                .kpHD(dto == null ? null : dto.getKpHD())
                .imdb(dto == null ? null : dto.getImdb())
                .tmdb(dto == null ? null : dto.getTmdb())
                .build();

    }
}
