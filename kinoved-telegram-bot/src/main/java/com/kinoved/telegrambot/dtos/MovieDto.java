package com.kinoved.telegrambot.dtos;

import com.kinoved.common.kinopoisk.dtos.movies.LinkedMovieDtoV14;

import com.kinoved.common.kinopoisk.dtos.movies.details.AudienceDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.CurrencyValueDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.FactInMovieDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.FeesDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.LogoDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.NameDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.NetworksDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.PersonInMovieDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.PremiereDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.RatingDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.ReviewInfoDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.SeasonInfoDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.ShortImageDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.VideoTypesDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.VotesDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.WatchabilityDto;
import com.kinoved.common.kinopoisk.dtos.movies.details.YearRangeDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MovieDto {
    private String id;
    private ExternalId externalId;

    // todo возможно оформить перечисление для этих полей
    private String type; // Тип тайтла. Доступны: movie | tv-series | cartoon | anime | animated-series | tv-show
    private Integer typeNumber; //Тип тайтла в числовом обозначении.
    // Доступны: 1 (movie) | 2 (tv-series) | 3 (cartoon) | 4 (anime) | 5 (animated-series) | 6 (tv-show)

    private String name; // Название тайтла по-русски
    private String alternativeName; // Название тайтла по-английски
    private String enName;
    private List<NameDto> names; // Названия тайтла на разных языках

    private String description; // Описание тайтла
    private String shortDescription; // Сокращенное описание
    private String slogan; // Слоган

    //todo оформить отдельные сущности жанров и стран (загружать их по апи кинопоиска)
    private List<String> genres; // Жанры
    private List<String> countries; // Страны

    private Integer year; // Год премьеры. При поиске по этому полю, можно использовать интервалы 1860-2030
    private List<YearRangeDto> releaseYears;

    private Integer movieLength; // Продолжительность фильма
    private Boolean isSeries; // Признак сериала
    private Integer totalSeriesLength; // Продолжительность всех серий
    private Integer seriesLength; // Средняя продолжительность серии

    private LogoDto logo;
    private ShortImageDto poster;
    private ShortImageDto backdrop;
    private VideoTypesDto videos; // trailers, teasers

    private List<PersonInMovieDto> persons;

    private String ratingMpaa; // Возрастной рейтинг по MPAA
    private Integer ageRating; // Возрастной рейтинг

    private RatingDto rating;
    private VotesDto votes;
    private Integer top10; // Позиция тайтла в топ 10. Чтобы найти фильмы, участвующие в рейтинге используйте: !null
    private Integer top250; // Позиция тайтла в топ 250. Чтобы найти фильмы, участвующие в рейтинге используйте: !null
    private List<String> lists; // Список коллекций, в которых находится тайтл.
    private List<ReviewInfoDto> reviewInfo;
    private List<SeasonInfoDto> seasonsInfo;

    private List<LinkedMovieDtoV14> similarMovies; // Список похожих фильмов
    private List<LinkedMovieDtoV14> sequelsAndPrequels; // Сиквелы и приквелы

    private WatchabilityDto watchability; //

    private CurrencyValueDto budget;
    private FeesDto fees;
    private PremiereDto premiere;
    private Boolean ticketsOnSale; // Признак того, что тайтл находится в прокате
    private List<AudienceDto> audience;
    private NetworksDto networks;

    private List<FactInMovieDto> facts;
    private String status; // Статус релиза тайтла.
    // Доступные значения: filming | pre-production | completed | announced | post-production

    private String kpCreatedAt; // ($date-time) дата создания на кинопоиске
    private String kpUpdatedAt; // ($date-time) дата обновления на кинопоиске

    private Date createdAt; // ($date-time) дата создания
    private Date updatedAt; // ($date-time) дата обновления
}
