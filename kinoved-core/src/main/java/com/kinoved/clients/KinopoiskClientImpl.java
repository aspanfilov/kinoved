package com.kinoved.clients;

import com.kinoved.common.kinopoisk.dtos.movies.MovieDtoV14;
import com.kinoved.config.props.KinopoiskProps;
import com.kinoved.common.kinopoisk.dtos.movies.SearchMovieResponseDtoV14;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class KinopoiskClientImpl implements KinopoiskClient {

    private static final int DEFAULT_PAGE = 1;

    private static final int DEFAULT_LIMIT = 10;

    private final WebClient webClient;

    private final KinopoiskProps kinopoiskProps;

    @Override
    public SearchMovieResponseDtoV14 searchMovie(String query, Integer page, Integer limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/%s/%s".formatted(
                                kinopoiskProps.getVersion(),
                                kinopoiskProps.getPaths().getSearchMovies()))
                        .queryParam("query", query)
                        .queryParam("page", page == null ? DEFAULT_PAGE : page)
                        .queryParam("limit", limit == null ? DEFAULT_LIMIT : limit)
                        .build())
                .retrieve()
                .bodyToMono(SearchMovieResponseDtoV14.class)
                .block();
    }

    @Override
    public MovieDtoV14 getMovieById(Long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/%s/%s/%s".formatted(
                                kinopoiskProps.getVersion(),
                                kinopoiskProps.getPaths().getMovieDetails(),
                                id.toString()))
                        .build())
                .retrieve()
                .bodyToMono(MovieDtoV14.class)
                .block();
    }
}
