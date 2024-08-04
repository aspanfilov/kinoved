package com.kinoved.mappers;

import com.kinoved.common.kinopoisk.dtos.movies.details.ItemNameDto;
import com.kinoved.common.kinopoisk.dtos.movies.MovieDtoV14;
import com.kinoved.models.MovieData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MovieDataMapper implements CommonMapper<MovieData, MovieDtoV14> {

    //todo рассмотреть MapStruct чтобы делать конвертер dto в сущности и наоборот

    private final ExternalIdMapper externalIdMapper;

    @Override
    public MovieData toEntity(MovieDtoV14 dto) {
        if (dto == null) {
            return null;
        }

        return MovieData.builder()
                .id(null)
                .externalId(externalIdMapper.toEntity(dto.getId(), dto.getExternalId()))
                .type(dto.getType())
                .typeNumber(dto.getTypeNumber())
                .name(dto.getName())
                .alternativeName(dto.getAlternativeName())
                .enName(dto.getEnName())
                .names(dto.getNames())
                .description(dto.getDescription())
                .shortDescription(dto.getShortDescription())
                .slogan(dto.getSlogan())
                .genres(dto.getGenres().stream().map(ItemNameDto::getName).toList())
                .countries(dto.getCountries().stream().map(ItemNameDto::getName).toList())
                .year(dto.getYear())
                .releaseYears(dto.getReleaseYears())
                .movieLength(dto.getMovieLength())
                .isSeries(dto.getIsSeries())
                .totalSeriesLength(dto.getTotalSeriesLength())
                .seriesLength(dto.getSeriesLength())
                .logo(dto.getLogo())
                .poster(dto.getPoster())
                .backdrop(dto.getBackdrop())
                .videos(dto.getVideos())
                .persons(dto.getPersons())
                .ratingMpaa(dto.getRatingMpaa())
                .ageRating(dto.getAgeRating())
                .rating(dto.getRating())
                .votes(dto.getVotes())
                .top10(dto.getTop10())
                .top250(dto.getTop250())
                .lists(dto.getLists())
                .reviewInfo(dto.getReviewInfo())
                .seasonsInfo(dto.getSeasonsInfo())
                .similarMovies(dto.getSimilarMovies())
                .sequelsAndPrequels(dto.getSequelsAndPrequels())
                .watchability(dto.getWatchability())
                .budget(dto.getBudget())
                .fees(dto.getFees())
                .premiere(dto.getPremiere())
                .ticketsOnSale(dto.getTicketsOnSale())
                .audience(dto.getAudience())
                .networks(dto.getNetworks())
                .facts(dto.getFacts())
                .status(dto.getStatus())
                .kpUpdatedAt(dto.getUpdatedAt())
                .kpCreatedAt(dto.getCreatedAt())
                .updatedAt(null) // будет установлено при сохранении в MongoDB
                .createdAt(null) // будет установлено при сохранении в MongoDB
                .build();
    }

}
