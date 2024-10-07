package com.kinoved.mappers;

import com.kinoved.common.kinopoisk.dtos.movies.details.ExternalIdDto;
import com.kinoved.models.moviedetails.ExternalId;

public interface ExternalIdMapper {
    ExternalId toEntity(Long kpDevId, ExternalIdDto dto);
}
