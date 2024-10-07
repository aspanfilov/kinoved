package com.kinoved.common.kinopoisk.dtos.movies.details;

import lombok.Data;

import java.util.List;

@Data
public class WatchabilityDto {
    private List<WatchabilityItemDto> items;
}
